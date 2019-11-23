package tools;

public class Complex {

	private float real;
	private float imaginary;

	public Complex() {
		this.real = 0.0f;
		this.imaginary = 0.0f;
	}

	public Complex(float real, float imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public Complex(double real, double imaginary) {
		this.real = (float) real;
		this.imaginary = (float) imaginary;
	}

	public float getMagnitude() {
		return (float) (Math.sqrt(real * real + imaginary * imaginary));
	}

	public float getArg() {// range (-pi,pi]
		float angle = (float) Math.abs(Math.atan(imaginary / real));
		if (real > 0 && imaginary > 0) {// q1
			return angle;
		}
		if (real < 0 && imaginary > 0) {// q2
			return (float) (Math.PI - angle);
		}
		if (real < 0 && imaginary < 0) {// q3
			return (float) (-Math.PI + angle);
		}
		if (real > 0 && imaginary < 0) {// q4
			return -angle;
		}
		if (real == 0 && imaginary > 0) {
			return (float) (Math.PI / 2.0f);
		}
		if (real == 0 && imaginary < 0) {
			return (float) (-Math.PI / 2.0f);
		}
		if (real > 0 && imaginary == 0) {
			return (float) (0.0f);
		}
		if (real < 0 && imaginary == 0) {
			return (float) (Math.PI);
		}
		return (float) 0;
	}

	public float getReal() {
		return real;
	}

	public void setReal(float real) {
		this.real = real;
	}

	public float getImaginary() {
		return imaginary;
	}

	public void setImaginary(float imaginary) {
		this.imaginary = imaginary;
	}

	public String toString() {
		return real + " + " + imaginary + "i";
	}

	public static Complex sin(Complex z) {
		return new Complex(Math.sin(z.getReal()) * (Math.exp(-z.getImaginary()) + Math.exp(z.getImaginary())) / 2.0f,
				-Math.cos(z.getReal()) * (Math.exp(-z.getImaginary()) - Math.exp(z.getImaginary())) / 2.0f);
	}

	public static Complex cos(Complex z) {
		return new Complex(Math.cos(z.getReal()) * (Math.exp(-z.getImaginary()) + Math.exp(z.getImaginary())) / 2.0f,
				Math.sin(z.getReal()) * (Math.exp(-z.getImaginary()) - Math.exp(z.getImaginary())) / 2.0f);
	}

	public static Complex tan(Complex z) {
		return divide(sin(z), cos(z));
	}

	public static Complex expi(float theta) {
		return new Complex(Math.cos(theta), Math.sin(theta));
	}

	public static Complex expi(double theta) {
		return new Complex(Math.cos(theta), Math.sin(theta));
	}

	public static Complex exp(Complex z) {
		return multiply(expi(z.getImaginary()), (float) Math.exp(z.getReal()));
	}

	public static Complex ln(Complex z) {
		return new Complex(Math.log(z.getMagnitude()), z.getArg());
	}

	public static Complex multiply(Complex z, Complex w) {
		return new Complex(z.getReal() * w.getReal() - z.getImaginary() * w.getImaginary(),
				z.getReal() * w.getImaginary() + z.getImaginary() * w.getReal());
	}

	public static Complex multiply(Complex z, float scale) {
		return new Complex(z.getReal() * scale, z.getImaginary() * scale);
	}

	public static Complex divide(Complex z, Complex w) {
		return multiply(z, reciprocate(w));
	}

	public static Complex reciprocate(Complex z) {
		return multiply(new Complex(z.getReal(), -z.getImaginary()),
				1.0f / (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary()));
	}

	public static Complex add(Complex z, Complex w) {
		return new Complex(z.getReal() + w.getReal(), z.getImaginary() + w.getImaginary());
	}

	public static Complex power(Complex z, Complex w) {
		return exp(multiply(w, ln(z)));
	}

	public static Complex power(Complex z, float n) {
		return exp(multiply(ln(z), n));
	}
}
