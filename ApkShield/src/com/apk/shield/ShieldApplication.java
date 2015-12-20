package com.apk.shield;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import dalvik.system.PathClassLoader;
public class ShieldApplication extends Application
{
	private Field field;
	//	private String mDexPath = "/android_asset/Test.apk";
    private String mDexPath = "";
    
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	
    }
    
    public void copyLib(String src,String dst){
    	try {
			ByEncryptAndDecrypt.decrypt(src,"AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746",dst);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void copyLib1(
			String src ,
			String dst )
	{
		try
		{
			File out = new File( dst );
			if( out.exists() )
			{
				return;
			}
			InputStream fis = this.getAssets().open( src );			
			FileOutputStream fos = new FileOutputStream( out );
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while( ( len = fis.read( buffer ) ) != 0 )
//			{
//				fos.write( buffer , 0 , len );
//			}
			
			 //得到数据的大小  
		    int length = fis.available();      
		    byte [] bytIn = new byte[length]; 
		    fis.read(bytIn);
			
		    fos.write(bytIn, 0, length);
		    
			fis.close();
			fos.close();
		}
		catch( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void attachBaseContext(
			Context base )
	{
		
		// TODO Auto-generated method stub
		super.attachBaseContext( base );
		ByEncryptAndDecrypt.doInit(this);
		File dir = getFilesDir();
		String path = dir.getAbsolutePath() + "/.apk";
		System.out.println(path);
		copyLib( "data.tts" , path );
		mDexPath = path;
		Context context = getBaseContext();
		Field loadedApkField;
		try
		{			
			loadedApkField = context.getClass().getDeclaredField( "mPackageInfo" );
			loadedApkField.setAccessible( true );
			Object mPackageInfo = loadedApkField.get( context );
			field = mPackageInfo.getClass().getDeclaredField( "mClassLoader" );
			field.setAccessible( true);
			//拿到originalclassloader  
			Object mClassLoader = field.get( mPackageInfo );
			//创建自定义的classloader  
			ClassLoader loader = new ShieldClassLoader( mDexPath , this , this.getApplicationInfo().sourceDir , (PathClassLoader)mClassLoader );
			//替换originalclassloader为自定义的classloader  
			field.set( mPackageInfo , loader );
			//			
			AssetManager am;
			System.out.println("dex--path--:"+mDexPath);
			am = (AssetManager)AssetManager.class.newInstance();
			am.getClass().getMethod( "addAssetPath" , String.class ).invoke( am , mDexPath );
			Resources rs = context.getResources();
			Resources res = new Resources( am , rs.getDisplayMetrics() , rs.getConfiguration() );
			Field field = mPackageInfo.getClass().getDeclaredField( "mResources" );
			field.setAccessible( true );
			field.set( mPackageInfo , res );
			//			loadResources();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public AssetManager getAssets()
	{
		//		return mAssetManager == null ? super.getAssets() : mAssetManager;
		Log.d( "MM" , "getAssets" );
		return super.getAssets();
	}
	
	@Override
	public Resources getResources()
	{
		//		return mResources == null ? super.getResources() : mResources;
		Log.d( "MM" , "getAssets" );
		return super.getResources();
	}
}
