package dev.lelek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ByteRequestUtils {
    public static int firstIndexOf(byte[] arr, byte[] toSearch) {
        // TODO can maybe be done faster
        for (int i = 0; i < arr.length - toSearch.length + 1; i++) {
            int matches = 0;
            for (int j = 0; j < toSearch.length; j++) {
                if (arr[i + j] != toSearch[j]) {
                    break;
                } else {
                    matches += 1;
                }
            }
            if (matches == toSearch.length) {
                return i;
            }
        }
        return -1;
    }

    public static byte[] addArray(byte[] arr, byte[] toAdd) {
        byte[] newArr = new byte[arr.length + toAdd.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        System.arraycopy(toAdd, 0, newArr, arr.length, toAdd.length);
        return newArr;
    }

    public static String bytesToString(byte[] request) {
        StringBuilder out = new StringBuilder();
        for (byte b : request) {
            out.append((char) b);
        }
        return out.toString();
    }

    public static String bytesToString(byte[] bytes, int from, int to) {
        StringBuilder out = new StringBuilder();
        for (int i = from; i <= to; i++) {
            out.append((char) bytes[i]);
        }
        return out.toString();
    }

    public static int getHeadersEndIndex(byte[] bytes) {
        return firstIndexOf(bytes, new byte[]{'\r', '\n', '\r', '\n'}) - 1;
    }

    public static byte[] stringToBytes(String s) {
        byte[] byteArray = new byte[s.length()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) s.charAt(i);
        }
        return byteArray;
    }

    public static byte[] fileToByteArray(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }
}
