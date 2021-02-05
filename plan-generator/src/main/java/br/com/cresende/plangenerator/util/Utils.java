package br.com.cresende.plangenerator.util;

import java.util.Objects;

public class Utils {

  public static void checkForNull(Object... toBeChecked) {
    if (toBeChecked == null) {
      throw new IllegalArgumentException("Nothing to check");
    }
    for (Object check : toBeChecked) {
      if (Objects.isNull(check)) {
        throw new IllegalArgumentException("None argument can be null");
      }
    }
  }
}
