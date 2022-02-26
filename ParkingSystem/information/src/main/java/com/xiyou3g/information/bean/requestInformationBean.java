package com.xiyou3g.information.bean;

public class requestInformationBean {


    private String id;
    private String nickname;
    private String sex;
    private String birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;

    public requestInformationBean(String id, String nickname, String sex,
                                  String birthday, String country, String province,
                                  String city, String district, String description) {
        this.id = id;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"id\":\"" + id + "\",\n\n" +
                "\t\"nickname\":\"" + nickname + "\",\n\n" +
                "\t\"sex\""+":"+sex+ ",\n\n" +
                "\t\"birthday\":\"" + birthday + "\",\n\n" +
                "\t\"country\":\"" + country + "\",\n\n" +
                "\t\"province\":\"" + province + "\",\n\n" +
                "\t\"city\":\"" + city + "\",\n\n" +
                "\t\"district\":\"" + district + "\",\n\n" +
                "\t\"description\":\"" + description + "\"\n\n" +
                "}";
     }
}

    /*{
        "id": "946468093863919616",

            "nickname": "CodePianist",

            "sex": 1,

            "birthday": "2002-03-23T14:18:46.015+00:00",

            "country": "中国",

            "province": "陕西省",

            "city": "西安市",

            "district": "长安区",

            "description": "手执烟火谋生活，心怀诗意以谋爱"

    }*/

