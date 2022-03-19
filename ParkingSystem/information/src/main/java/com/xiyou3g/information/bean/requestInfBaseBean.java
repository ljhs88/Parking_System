package com.xiyou3g.information.bean;

import android.text.Editable;

public class requestInfBaseBean {

    private String status;
    private String msg;
    private String success;
    public data data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }

    public data getData() {
        return data;
    }

    public static class data {
        private String id;
        private String name;
        private String identity;
        private String image1;
        private String image2;
        private String car1;
        private String car2;
        private String car3;

        public data(String id, String name, String identity,
                                  String image1, String image2, String car1,
                                  String car2, String car3) {
            this.id = id;
            this.name = name;
            this.identity = identity;
            this.image1 = image1;
            this.image2 = image2;
            this.car1 = car1;
            this.car2 = car2;
            this.car3 = car3;
        }

        @Override
        public String toString() {
            return "{\n" +
                    "\t\"id\"" + ":" + id + ",\n\n" +
                    "\t\"name\":\"" + name + "\",\n\n" +
                    "\t\"identity\":\"" + identity + "\",\n\n" +
                    "\t\"image1\"" + ":" + image1 + ",\n\n" +
                    "\t\"image2\"" + ":" + image2 + ",\n\n" +
                    "\t\"car1\":\"" + car1 + "\",\n\n" +
                    "\t\"car2\"" + ":" + car2 + ",\n\n" +
                    "\t\"car3\"" + ":" + car3 + "\n\n" +
                    "}";
        }

    }

    @Override
    public String toString() {
        return "requestInfBaseBean{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", success='" + success + '\'' +
                ", data=" + data +
                '}';
    }
}
