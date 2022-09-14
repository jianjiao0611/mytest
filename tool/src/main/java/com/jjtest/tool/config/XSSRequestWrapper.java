package com.jjtest.tool.config;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class XSSRequestWrapper extends HttpServletRequestWrapper {

        private String body;
        private static String[] NO_CHECK_PARAMETER_NAME_LIST = new String[]{};

        private static String[] NO_CHECK_URL_LIST = new String[]{"/test1"};

        private static List<Pattern> NO_CHECK_URL_PATTERN_LIST = Arrays.stream(NO_CHECK_URL_LIST).map(item -> Pattern.compile(item)).collect(Collectors.toList());

        public XSSRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();

            try (InputStream inputStream = request.getInputStream();
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
                char[] charBuff = new char[128];
                int red;
                if ((red = bufferedReader.read(charBuff)) > 0) {
                    stringBuilder.append(charBuff, 0, red);
                }

            } catch (Exception e) {
                throw new RuntimeException();
            }
            body = Jsoup.clean(stringBuilder.toString(), Whitelist.basic());
        }


        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() {
                    return bais.read();
                }
            };
        }


}
