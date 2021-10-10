package pinpon;

public class PinponMain {

	public static void main(String[] args) {
	PinponPaint pin = new PinponPaint();
	Thread th = new Thread(pin);
	Thread p1th = new Thread(pin.p1);
	Thread p2th = new Thread(pin.p2);
	th.start();
	p1th.start();
	p2th.start();
	}

}
