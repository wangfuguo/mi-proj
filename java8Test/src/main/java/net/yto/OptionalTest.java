package net.yto;

import java.util.Optional;

/**
 * @author 00938658-王富国
 * @date 2017/4/11
 */
public class OptionalTest {

    public static void main(String[] args){

        OptionalTest optionalTest = new OptionalTest();

        User user = null;
        System.out.println(optionalTest.getPhoneByUser7(user));
        System.out.println(optionalTest.getPhoneByUser8(user));


    }

    public String getPhoneByUser7(User user) {
        if(user == null) {
            return "000000";
        }
        if(user.getMobilePhone() != null && !user.getMobilePhone().isEmpty()) {
            return user.getMobilePhone();
        }
        if(user.getPhone() != null && !user.getPhone().isEmpty()) {
            return user.getPhone();
        }
        return "000000";
    }

    public String getPhoneByUser8(User user) {
        Optional<User> userOptional = Optional.ofNullable(user);
        //flatMap:如果值存在，返回基于Optional包含的映射方法的值，否则返回一个空的Optional
        //filter:如果值存在，并且这个值匹配给定的 predicate，返回一个Optional用以描述这个值，否则返回一个空的
        Optional<String> mobilePhone = userOptional.flatMap((u) ->
                Optional.ofNullable(u.getMobilePhone())).filter(s -> !s.isEmpty());
        Optional<String> phone = userOptional.flatMap(u ->
                Optional.ofNullable(u.getPhone()).filter(s -> !s.isEmpty()));
        //orElse: 如果存在该值，返回值， 否则返回 other。
        return mobilePhone.orElse(phone.orElse("000000"));
    }
}

class User {
    private String mobilePhone;
    private String phone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getMobilePhone().equals(user.getMobilePhone())) return false;
        return getPhone().equals(user.getPhone());
    }

    @Override
    public int hashCode() {
        int result = getMobilePhone().hashCode();
        result = 31 * result + getPhone().hashCode();
        return result;
    }
}

