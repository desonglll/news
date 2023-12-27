package com.common.dbaccess.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.common.dbaccess.util.JsonAdapt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 封装类BaseHttpServlet
 * 模仿SSM的SpringMvc
 *
 * @author eclipse
 */
@SuppressWarnings("serial")
public abstract class BaseHttpServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String base = req.getContextPath();
        String uri = req.getRequestURI();
        if (uri.contains(".")) {
            uri = uri.split("\\.")[0];
        }
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        String m = uri.substring(uri.indexOf("_") + 1);
        Method method = null;
        try {
            method = getClass().getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Servlet封装类: \"" + m + "\"方法，不存在", e);
        }
        try {
            String type = "f";
            String path = (String) method.invoke(this, req, resp);
            if (path != null && !"".equals(path = path.trim())) {
                int idx = path.indexOf(":");
                if (idx >= 0) {
                    type = path.substring(0, idx);
                    path = path.substring(idx + 1);
                }
                if ("f".equals(type)) {
                    req.getRequestDispatcher(path).forward(req, resp);
                } else {
                    resp.sendRedirect(base + path);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Servlet封装类: \"" + m + "\"方法执行失败", e);
        }
    }

    public String forward(String url) {
        return ("f:/" + url).replace("//", "/");
    }

    public String redirect(String url) {
        return ("r:/" + url).replace("//", "/");
    }

    public boolean isPost(HttpServletRequest req) {
        return "POST".equalsIgnoreCase(req.getMethod());
    }

    public void respJson(String json, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = null;
        try {
            pw = resp.getWriter();
            pw.print(json);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public String jsAlert(String msg, String url, HttpServletResponse resp) {
        respJson("<script>alert('" + msg + "');window.location.href='" + url + "'</script>", resp);
        return null;
    }

    public String jsAlert(String msg, HttpServletResponse resp) {
        respJson("<script>alert('" + msg + "');window.history.back();</script>", resp);
        return null;
    }

    // 打印JSON
    public static void print(Object obj) {
        System.out.println(obj instanceof String ? (String) obj : new Gson().toJson(obj));
    }

    // 打印JSON并格式化
    public static void System_out_println(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(obj instanceof String ? (String) obj : gson.toJson(obj));
    }

    // 打印JSON到网页中
    public String outRespJson(Object obj, HttpServletResponse resp) {
        String json = obj instanceof String ? (String) obj : new Gson().toJson(obj);
        respJson(json, resp);
        return null;
    }

    // 适用于Jetty服务器
    public String copyUploadFile(HttpServletRequest req, String filePath) {
        String fileName = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            Part part = req.getPart("file");
            fis = part.getInputStream();
            fileName = part.getSubmittedFileName();
            filePath += "/" + fileName;
            fos = new FileOutputStream(filePath);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public String copyUpload(HttpServletRequest req, String CDdirPath) {
        return copyUpload(req, CDdirPath, "file");
    }

    public String copyUpload(HttpServletRequest req, String CDdirPath, String col) {
        String CD = CDdirPath.contains(":") ? (CDdirPath.split(":")[0] + ":") : "";
        String dirPath = CDdirPath.contains(":") ? CDdirPath.split(":")[1] : CDdirPath;
        String filePath = null;
        try {
            Part part = req.getPart(col);
            filePath = dirPath + "/" + System.currentTimeMillis() + part.getSubmittedFileName();
            part.write(CD + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    protected <T> T getParameter(HttpServletRequest req, Class<T> clazz) {
        Map<String, String[]> map = req.getParameterMap();
        Map<String, String> ret = new HashMap<String, String>();
        Set<String> set = map.keySet();
        for (String key : set) {
            String[] vals = (String[]) map.get(key);
            String value = "";
            for (String val : vals) {
                if (!"".equals(value))
                    value += ",";
                value += val;
            }
            ret.put(key, value);
        }
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Integer.class, new JsonAdapt()).
                registerTypeAdapter(int.class, new JsonAdapt()).create();
        return gson.fromJson(gson.toJson(ret), clazz);
    }

    protected String getStringParam(HttpServletRequest req, String key) {
        return req.getParameter(key);
    }

    protected Integer getIntParam(HttpServletRequest req, String key) {
        String val = getStringParam(req, key);
        try {
            return val == null ? null : Integer.parseInt(val);
        } catch (Exception e) {
            return null;
        }
    }

    protected Integer getId(HttpServletRequest req) {
        return getIntParam(req, "id");
    }

    protected Integer getCurpage(HttpServletRequest req) {
        String curpage = req.getParameter("curpage");
        int cp = curpage == null ? 1 : Integer.parseInt(curpage);
        if (cp <= 0) cp = 1;
        return cp;
    }

}
