package com.hexacta.updatecoins.util;

import java.io.File;

public class JarUtilities {
  public static String getJarName()
  {
    return new File(JarUtilities.class.getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .getPath())
        .getName();
  }

  public static boolean runningFromJar()
  {
    return getJarName().contains(".jar");
  }

  public static boolean runningFromTarget()
  {
    return getJarName().contains("target");
  }
}
