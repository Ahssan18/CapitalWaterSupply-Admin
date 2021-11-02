package com.saproductions.wallpapersworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.wallpapersworld.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FullImageActivity extends AppCompatActivity {

    private ImageView fullImage;
    private Button apply;
    private String url;
    private AdView adView;
    private final String TAG = "GGLADS";
    InterstitialAd mInterstitialAd;

    private com.facebook.ads.InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);

        interstitialAds();
        bannerAds();


        fullImage = findViewById(R.id.fullImage);
        url = getIntent().getStringExtra("image");
        Button downloadImg = findViewById(R.id.download);

        apply = findViewById(R.id.apply);

        interstitialAd = new com.facebook.ads.InterstitialAd(this, "293391095817981_293442149146209");

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

        Glide.with(this).load(getIntent().getStringExtra("image")).into(fullImage);

        PRDownloader.initialize(getApplicationContext());

        downloadImg.setOnClickListener(v ->{
            checkPermission();

        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground();
            }
        });
    }

    private void interstitialAds() {

        AdRequest adRequest = new AdRequest.Builder().build();

        createInterstitialAd(adRequest);
    }

    private void createInterstitialAd (AdRequest adRequest){
        InterstitialAd.load(this,"ca-app-pub-2826157256232030/2477315255", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                Log.d(TAG, "onAdLoaded");
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(FullImageActivity.this);
                } else {
                    Log.d(TAG, "The interstitial ad wasn't ready yet.");
                }
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d(TAG, "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d(TAG, "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d(TAG, "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    private void bannerAds() {
        adView.loadAd(new AdRequest.Builder().build());

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed: ");
            }

            @Override
            public void onAdOpened() {
                Log.d(TAG, "onAdOpened: ");

            }

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");

            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked: ");

            }

            @Override
            public void onAdImpression() {
                Log.d(TAG, "onAdImpression: ");

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "onAdFailedToLoad: "+loadAdError.getMessage());
            }
        });
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    downloadImage();
                }else {
                    Toast.makeText(FullImageActivity.this, "PLease allow all permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }

        }).check();
    }

    private void downloadImage() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Downloading...");
        pd.setCancelable(false);
        pd.show();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File file = this.getExternalFilesDir(null);
        file.mkdirs();

        PRDownloader.download(url, file.getPath(), URLUtil.guessFileName(url, null, null))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        long per = progress.currentBytes*100 / progress.totalBytes;

                        pd.setMessage("Downloading: "+per+"%");

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        pd.dismiss();
                        Toast.makeText(FullImageActivity.this, "Downloading completed!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        pd.dismiss();
                        Toast.makeText(FullImageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }


                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_option, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.share:
                BitmapDrawable drawable = (BitmapDrawable)fullImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
                Uri uri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg/png/gif");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.putExtra(Intent.EXTRA_TEXT, "Google Playstore Link: https://play.google.com/store/apps/details?id="+getPackageName());
                startActivity(Intent.createChooser(intent, "Share"));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBackground() {  //wait i am copying code
        Bitmap bitmap = ((BitmapDrawable)fullImage.getDrawable()).getBitmap();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap yourbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(yourbitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
//
//        prepareBitmap(bitmap, manager);
//
//        try {
//            manager.setBitmap(bitmap);
//        } catch (IOException e) {
//            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
    }
    // Crop or inflate bitmap to desired device height and/or width
    public Bitmap prepareBitmap(final Bitmap sampleBitmap,
                                final WallpaperManager wallpaperManager) {
        Bitmap changedBitmap = null;
        final int heightBm = sampleBitmap.getHeight();
        final int widthBm = sampleBitmap.getWidth();
        final int heightDh = wallpaperManager.getDesiredMinimumHeight();
        final int widthDh = wallpaperManager.getDesiredMinimumWidth();
        if (widthDh > widthBm || heightDh > heightBm) {
            final int xPadding = Math.max(0, widthDh - widthBm) / 2;
            final int yPadding = Math.max(0, heightDh - heightBm) / 2;
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            final int[] pixels = new int[widthBm * heightBm];
            sampleBitmap.getPixels(pixels, 0, widthBm, 0, 0, widthBm, heightBm);
            changedBitmap.setPixels(pixels, 0, widthBm, xPadding, yPadding,
                    widthBm, heightBm);
        } else if (widthBm > widthDh || heightBm > heightDh) {
            changedBitmap = Bitmap.createBitmap(widthDh, heightDh,
                    Bitmap.Config.ARGB_8888);
            int cutLeft = 0;
            int cutTop = 0;
            int cutRight = 0;
            int cutBottom = 0;
            final Rect desRect = new Rect(0, 0, widthDh, heightDh);
            Rect srcRect = new Rect();
            if (widthBm > widthDh) { // crop width (left and right)
                cutLeft = (widthBm - widthDh) / 2;
                cutRight = (widthBm - widthDh) / 2;
                srcRect = new Rect(cutLeft, 0, widthBm - cutRight, heightBm);
            } else if (heightBm > heightDh) { // crop height (top and bottom)
                cutTop = (heightBm - heightDh) / 2;
                cutBottom = (heightBm - heightDh) / 2;
                srcRect = new Rect(0, cutTop, widthBm, heightBm - cutBottom);
            }
            final Canvas canvas = new Canvas(changedBitmap);
            canvas.drawBitmap(sampleBitmap, srcRect, desRect, null);

        } else {
            changedBitmap = sampleBitmap;
        }
        return changedBitmap;
    }
}