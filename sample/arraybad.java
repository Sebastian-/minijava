class Main {
    public static void main(String[] args) {
	System.out.println(new Foo().doit());
    }
}
class Foo {
    public int doit() {
	int [] arr;
	arr = new int[2];
	arr[0] = 0;
	arr[1] = 1;
	arr[2] = 2;
	return 0;
    }
}
