package com.apk.shield;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import dalvik.system.PathClassLoader;

public class SingleClass extends PathClassLoader {

	public SingleClass(String dexPath, ClassLoader parent) {
		super(dexPath, parent);
		// TODO Auto-generated constructor stub
	}

	public SingleClass(String dexPath, String libraryPath, ClassLoader parent) {
		super(dexPath, libraryPath, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void clearAssertionStatus() {
		// TODO Auto-generated method stub
		super.clearAssertionStatus();
	}

	@Override
	protected Package definePackage(String name, String specTitle,
			String specVersion, String specVendor, String implTitle,
			String implVersion, String implVendor, URL sealBase)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return super.definePackage(name, specTitle, specVersion, specVendor, implTitle,
				implVersion, implVendor, sealBase);
	}

	@Override
	protected Package[] getPackages() {
		// TODO Auto-generated method stub
		return super.getPackages();
	}

	@Override
	public URL getResource(String resName) {
		// TODO Auto-generated method stub
		return super.getResource(resName);
	}

	@Override
	public InputStream getResourceAsStream(String resName) {
		// TODO Auto-generated method stub
		return super.getResourceAsStream(resName);
	}

	@Override
	public Enumeration<URL> getResources(String resName) throws IOException {
		// TODO Auto-generated method stub
		return super.getResources(resName);
	}

	@Override
	protected Class<?> loadClass(String className, boolean resolve)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className, resolve);
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className);
	}

	@Override
	public void setClassAssertionStatus(String cname, boolean enable) {
		// TODO Auto-generated method stub
		super.setClassAssertionStatus(cname, enable);
	}

	@Override
	public void setDefaultAssertionStatus(boolean enable) {
		// TODO Auto-generated method stub
		super.setDefaultAssertionStatus(enable);
	}

	@Override
	public void setPackageAssertionStatus(String pname, boolean enable) {
		// TODO Auto-generated method stub
		super.setPackageAssertionStatus(pname, enable);
	}
	
	

}
