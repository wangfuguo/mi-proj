package net.yto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author 00938658-王富国
 * @date 2017/4/11
 */
public class MethodRefTest {

    public static void main(String[] args){

        //构造器引用：它的语法是Class::new，或者更一般的Class< T >::new
        Car car = Car.create(Car::new);
        List<Car> cars = Arrays.asList(car);
        //静态方法引用：它的语法是Class::static_method
        cars.forEach(Car::collide);
        for(Car car1 : cars) {
            car1.collide(car);
        }
        //特定类的任意对象的方法引用：它的语法是Class::method
        cars.forEach(Car::repair);
        //特定对象的方法引用：它的语法是instance::method
        final Car police = Car.create( Car::new );
        cars.forEach( police::follow );

        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");

        names.forEach(System.out::println);




    }

}

class Car {
    public static Car create( final Supplier< Car > supplier ) {
        return supplier.get();
    }

    public static void collide( final Car car ) {
        System.out.println( "Collided " + car.toString() );
    }

    public void follow( final Car another ) {
        System.out.println( "Following the " + another.toString() );
    }

    public void repair() {
        System.out.println( "Repaired " + this.toString() );
    }
}