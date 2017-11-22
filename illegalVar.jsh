public class VarFailure {

static void f (){}
static Object o () {return null;}

public static void main (String[] main){

var x;

var y=9, z=8;

var s = null;

var l = () -> {};

var m = java.lang.Math::pow;

var a = {7, 9, 10};

var f = f();

}

}