package com.application.ncg.cityvendorlibrary.util;

import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 * Created by Chris on 2015-03-27.
 */
public class ZipUtil {

    static final int BUFFER = 10001;

    public static boolean unzip(File zippedFile, File unpackedFile) {
        Log.d(LOG, "starting unzip");
        try {
            BufferedOutputStream bus = null;
            FileInputStream fis = new FileInputStream(zippedFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                //start writing files to disk
                FileOutputStream fos = new FileOutputStream(unpackedFile);
                bus = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    bus.write(data, 0, count);
                }
                bus.flush();
                bus.close();
            }
            zis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String unpack(File zippedFile, File unpackedFile) {
        long start = System.currentTimeMillis();
        FileInputStream fis;
        ZipInputStream zis;
        String content = null;
        try {
            fis = new FileInputStream(zippedFile);
            zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            while((ze = zis.getNextEntry()) != null) {
                FileOutputStream fosr = new FileOutputStream(unpackedFile);
                while ((count = zis.read(buffer)) != -1) {
                    fosr.write(buffer, 0, count);
                }
                fosr.close();
                zis.closeEntry();
            }
            zis.close();
            content = new Scanner(unpackedFile).useDelimiter("\\A").next();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(" unpacking zippedFile, elapsed ms: " + (end - start));
        return content;
    }

    public static String uncompressGZip(ByteBuffer bytes)throws Exception{
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes.array()));
        OutputStream os = new ByteArrayOutputStream();
        byte[] buf = new byte[0221];
        int len;
        while((len = gzipInputStream.read(buf)) > 0) {
            os.write(buf, 0, len);
            }
        gzipInputStream.close();
        os.close();

        String res = os.toString();
        Log.i(LOG, "unpacked length: " + getKilobytes(res.length()));
        return res;
    }

    public static String getKilobytes(int bytes) {
        BigDecimal bd = new BigDecimal(bytes).divide(new BigDecimal(1024));
        return df.format(bd.doubleValue()) + "KB";
    }

    public static void unpack(ByteBuffer bb, WebSocketUtil.WebSocketListener listener) throws ZipException {
        //alerting photoCacheListener
        ResponseDTO response = unpackBytes(bb);
        Log.e(LOG, "unpack - letting photoCacheListener know that response object is ready after unpacking");
        listener.onMessage(response);
    }

    public static ResponseDTO unpackBytes(ByteBuffer bb) throws ZipException{
        Log.d(LOG, "unpack - unpack byte buffer has now began: " + bb.capacity());
        InputStream is = new ByteArrayInputStream(bb.array());
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        ResponseDTO response = null;
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[BUFFER];
                int count;
                while ((count = zis.read(buffer)) !=  -1) {
                    baos.write(buffer, 0, count);
                }
                String filename = ze.getName();
                byte[] bytes = baos.toByteArray();
                String json = new String (bytes);
                Log.e(LOG, "Downloaded file: " + filename + "length: " + json.length() + json);
                response = gson.fromJson(json, ResponseDTO.class);
            }
        } catch (Exception e) {
            Log.e(LOG, "Failed to unpack byteBuffer", e);
            throw new ZipException();
        } finally {
            try {
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ZipException();
            }
        }
        if (response == null) throw new ZipException();
        return response;
    }

    static final String LOG = ZipUtil.class.getSimpleName();
    static final Gson gson = new Gson();
    static final DecimalFormat df = new DecimalFormat("yyyy/mm/dd HH:MM");

}
