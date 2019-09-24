package kr.forpet.view.recommend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;

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

    private File mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);

        mImageReceipt.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("이미지를 업로드합니다.");
            builder.setPositiveButton("앨범", ((dialog, which) -> {
                getImageAtAlbum();
                dialog.dismiss();
            }));

            builder.setNegativeButton("카메라", ((dialog, which) -> {
                getImageAtCamera();
                dialog.dismiss();
            }));

            builder.show();
        });

        mButtonEvaluate.setOnClickListener((v) -> {
            this.finish();
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
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_PICK_CAMERA:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(mPhoto));
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
                            .transform(new RotateTransformation(90)).into(mImageReceipt);
                }
            }
        }
    }

    private void getImageAtAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, REQUEST_PICK_ALBUM);
    }

    private void getImageAtCamera() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {
                File photo;

                try {
                    photo = createImageFile();
                    mPhoto = photo;

                    if (photo != null) {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "kr.forpet.provider", photo);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_PICK_CAMERA);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "외장메모리 미 지원", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private File createImageFile() throws IOException {
        String name = new SimpleDateFormat("yyyyMMdd-HH:mm:ss").format(new Date());
        return File.createTempFile(name, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
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
