package kr.forpet.view.recommend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.network.api.ForpetApiService;
import kr.forpet.util.UniqueIdentifiers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_ALBUM = 0;
    private static final int REQUEST_PICK_CAMERA = 1;

    @BindView(R.id.radiogroup_recommend_consensus)
    RadioGroup mRadioGroupConsensus;

    @BindView(R.id.radiogroup_recommend_facility)
    RadioGroup mRadioGroupFacility;

    @BindView(R.id.radiogroup_recommend_price)
    RadioGroup mRadioGroupPrice;

    @BindView(R.id.radiogroup_recommend_kindness)
    RadioGroup mRadioGroupKindness;

    @BindView(R.id.button_recommend_evaluate)
    Button mButtonEvaluate;

    @BindView(R.id.image_recommend_receipt)
    ImageView mImageReceipt;

    private File mLastPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);

        mImageReceipt.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("이미지를 업로드합니다.");

            builder.setPositiveButton("앨범", ((dialog, which) -> {
                getImageFromAlbum();
                dialog.dismiss();
            }));

            builder.setNegativeButton("카메라", ((dialog, which) -> {
                getImageFromCamera();
                dialog.dismiss();
            }));

            builder.show();
        });
        mButtonEvaluate.setOnClickListener((v) -> {
            if (mLastPhoto != null) {
                RequestBody userId = RequestBody.create(null, UniqueIdentifiers.getSettingSecureAndroidId(getApplicationContext()));
                RequestBody forpetHash = RequestBody.create(null, getIntent().getStringExtra("extra"));
                RequestBody point1 = RequestBody.create(null, mRadioGroupConsensus.getCheckedRadioButtonId() == R.id.radiobutton_recommend_consensus ? "1" : "0");
                RequestBody point2 = RequestBody.create(null, mRadioGroupFacility.getCheckedRadioButtonId() == R.id.radiobutton_recommend_facility ? "1" : "0");
                RequestBody point3 = RequestBody.create(null, mRadioGroupPrice.getCheckedRadioButtonId() == R.id.radiobutton_recommend_price ? "1" : "0");
                RequestBody point4 = RequestBody.create(null, mRadioGroupKindness.getCheckedRadioButtonId() == R.id.radiobutton_recommend_kindness ? "1" : "0");
                RequestBody point5 = RequestBody.create(null, "");
                RequestBody comment = RequestBody.create(null, "");

                RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), compressImageFile(mLastPhoto));
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file_image", mLastPhoto.getName(), body);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://forpets.kr/api/")
                        .build();

                ForpetApiService service = retrofit.create(ForpetApiService.class);
                service.satisfaction(userId, forpetHash, point1, point2, point3, point4, point5, comment, filePart).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i(ForpetApiService.class.getName(), response.body().string());

                            AlertDialog.Builder builder = new AlertDialog.Builder(RecommendActivity.this);
                            builder.setTitle("평가 완료");
                            builder.setMessage("만족도 평가가 정상적으로 접수되었습니다. 만족도 평가 데이터는 주단위로 반영됩니다. 감사합니다.");
                            builder.setNegativeButton("닫기", ((dialog, which) -> {
                                dialog.dismiss();
                                RecommendActivity.this.finish();
                            }));
                            builder.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RecommendActivity.this);
                        builder.setTitle("업로드 실패");
                        builder.setMessage("평가정보를 업로드 할 수 없습니다. 네트웍이 정상적으로 연결되었는지 확인후에 다시한번 시도해 주세요!");
                        builder.setNegativeButton("닫기", ((dialog, which) -> dialog.dismiss()));
                        builder.show();
                    }
                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecommendActivity.this);
                builder.setTitle("알림");
                builder.setMessage("영수증을 첨부해야지만 만족도평가를 할 수 있습니다.");
                builder.setNegativeButton("닫기", ((dialog, which) -> dialog.dismiss()));
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;

            switch (requestCode) {
                case REQUEST_PICK_ALBUM:
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(in);
                        in.close();

                        mLastPhoto = new File(getPathFromContentUri(data.getData()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_PICK_CAMERA:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(mLastPhoto));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            if (bitmap != null) {
                if (bitmap.getHeight() > bitmap.getWidth()) {
                    Glide.with(this)
                            .load(bitmap)
                            .into(mImageReceipt);
                } else {
                    Glide.with(this)
                            .load(bitmap)
                            .transform(new RotateTransformation(90))
                            .into(mImageReceipt);
                }
            }
        } else {
            if (mLastPhoto != null) {
                mLastPhoto.delete();
                mLastPhoto = null;
            }
        }
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, REQUEST_PICK_ALBUM);
    }

    private void getImageFromCamera() {
        // https://m.blog.naver.com/PostView.nhn?blogId=gyeom__&logNo=220815406153&proxyReferer=https%3A%2F%2Fwww.google.com%2F
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photo;

            try {
                photo = createImageFile();
                mLastPhoto = photo;

                if (photo != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "kr.forpet.provider", photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_PICK_CAMERA);
                    } else {
                        Uri uri = Uri.fromFile(photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_PICK_CAMERA);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "외장메모리 미 지원", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private File createImageFile() throws IOException {
        String prefix = new SimpleDateFormat("yyyyMMdd-HH:mm:ss").format(new Date()) + ".jpg";
        return File.createTempFile(prefix, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    private File compressImageFile(File imageFile) {
        if (imageFile.length() >= 1000000) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(imageFile)));

                OutputStream out = getContentResolver().openOutputStream(Uri.fromFile(imageFile));
                Bitmap.createScaledBitmap(bitmap, 1000, bitmap.getHeight() * 1000 / 1000, true).compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(BitmapFactory.class.getName(), imageFile.length() + "byte");
            return imageFile;
        }

        return imageFile;
    }

    private String getPathFromContentUri(Uri uri) {
        Cursor cursor = null;

        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);

            assert cursor != null;
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private class RotateTransformation extends BitmapTransformation {
        private float value;

        public RotateTransformation(float value) {
            this.value = value;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();
            matrix.postRotate(value);

            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(("rotate" + value).getBytes());
        }
    }
}
