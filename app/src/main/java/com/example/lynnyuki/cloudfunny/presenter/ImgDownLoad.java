package com.example.lynnyuki.cloudfunny.presenter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class ImgDownLoad {

    private static String filePath;
    private static Bitmap mBitmap;
    private static String mFileName;
    private static String mSaveMessage;
    private final static String TAG = "ImageActivity";
    private static Context context;

    private static ProgressDialog mSaveDialog = null;

    public static void donwloadImg(Context contexts,String filePaths){
        context = contexts;
        filePath = filePaths;
        mFileName = filePaths.substring(filePaths.lastIndexOf("/") + 1) + ".png";
        mSaveDialog = ProgressDialog.show(context, "保存图片", "图片正在保存中，请稍等...", true);

        new Thread(saveFileRunnable).start();
    }

    private static Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));

                saveFile(mBitmap, mFileName);

            } catch (IOException e) {
                mSaveMessage = "图片保存失败，请检查网络设置。";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }

    };

    @SuppressLint("HandlerLeak")
    private static Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            Log.d(TAG, mSaveMessage);
            Toast.makeText(context, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * Get image from newwork
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }


    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) throws IOException {
        File jia=new File(Environment.getExternalStorageDirectory().getPath() +"/CloudFunny");
        if(!jia.exists()){   //判断文件夹是否存在，不存在则创建
            jia.mkdirs();
        }
        File myCaptureFile = new File(jia +"/"+ fileName);
        if(myCaptureFile.exists()){

            mSaveMessage = "图片已经存在！";
            return mSaveMessage;
        }else {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            mSaveMessage = "图片成功保存至目录CloudFunny！";
            return mSaveMessage;
        }
    }
}