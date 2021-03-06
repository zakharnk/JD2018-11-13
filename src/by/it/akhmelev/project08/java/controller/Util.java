package by.it.akhmelev.project08.java.controller;

import by.it.akhmelev.project08.java.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

public class Util {

    static boolean checkUser(HttpServletRequest req) {
        return findUser(req) != null;
    }

    static User findUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object oUser = session.getAttribute("user");
            if (oUser != null) {
                return (User) oUser;
            }
        }
        return null;
    }

    static void saveFile(HttpServletRequest req, String fn) {
        fn = req.getServletContext().getRealPath("/image/" + fn);
        int size = 0;
        try (InputStream in = req.getPart("filebutton").getInputStream();
             OutputStream out = new FileOutputStream(fn)) {
            byte[] buffer = new byte[100000];
            while (in.available() > 0) {
                int n = in.read(buffer);
                out.write(buffer, 0, n);
                size += n;
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        if (size == 0) {
            //noinspection ResultOfMethodCallIgnored
            new File(fn).delete();
        }

    }
}
