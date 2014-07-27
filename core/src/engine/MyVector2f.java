package engine;

import org.lwjgl.util.vector.Vector2f;

/**
 * @author pavel.czempin
 * 
 */
public class MyVector2f {
	private Vector2f vector;
	private float angleRadians;
	private float length;

	/**
	 * creates an 2-dimensional vector using 2 coordinates
	 * 
	 * @param x
	 *            the x coordinate of the vector
	 * @param y
	 *            the y coordinate of the vector
	 */
	public MyVector2f(float x, float y) {
		this.vector = new Vector2f(x, y);

		this.length = (float) (Math.sqrt(x * x + y * y));

		this.angleRadians = calculateAngle(x, y);
		this.angleRadians = normalizeAngle(this.angleRadians);
	}

	public float getX() {
		return this.vector.getX();
	}

	public float getY() {
		return this.vector.getY();
	}

	public void setX(float x) {
		this.vector.setX(x);
		this.angleRadians = calculateAngle(x, this.vector.getY());
		this.angleRadians = normalizeAngle(this.angleRadians);
	}

	public void setY(float y) {
		this.vector.setY(y);
		this.angleRadians = calculateAngle(this.vector.getX(), y);
		this.angleRadians = normalizeAngle(this.angleRadians);
	}

	public float getLength() {
		return this.length;
	}

	/**
	 * add to the length of the vector without affecting the orientation
	 * 
	 * @param length
	 *            the length that gets added
	 */
	public void setLength(float length) {
		this.length = length;
		this.updateCoordinatesByLengthAndAngle();
	}

	public float getAngleRadians() {
		return this.angleRadians;
	}

	public float getAngleDegrees() {
		return this.calculateDegrees(this.angleRadians);
	}

	/**
	 * rotates the vector without changing the length
	 * 
	 * @param angle
	 *            the amount the vector is rotated by in radians
	 */
	public void rotateRadians(float angle) {
		this.angleRadians += angle;
		this.angleRadians = normalizeAngle(this.angleRadians);
		this.updateCoordinatesByLengthAndAngle();

	}

	/**
	 * rotates the vector without changing the length
	 * 
	 * @param angle
	 *            the amount the vector is rotated by in degrees
	 */
	public void rotateDegrees(float angle) {
		this.angleRadians += this.calculateRadians(angle);
		this.angleRadians = normalizeAngle(this.angleRadians);
		this.updateCoordinatesByLengthAndAngle();

	}

	/**
	 * Set the attribute angleRadians using degrees
	 * 
	 * @param angle
	 */
	public void setAngleDegrees(float angle) {
		this.angleRadians = this.calculateRadians(angle);
		this.angleRadians = normalizeAngle(this.angleRadians);
		this.updateCoordinatesByLengthAndAngle();

	}

	/**
	 * Set the attribute angleRadians using radians
	 * 
	 * @param angle
	 */
	public void setAngleRadians(float angle) {
		this.angleRadians = angle;
		this.angleRadians = normalizeAngle(this.angleRadians);
		this.updateCoordinatesByLengthAndAngle();

	}

	/**
	 * Updates the Coordinates using the current length and angle
	 */
	private void updateCoordinatesByLengthAndAngle() {
		float angle = this.angleRadians;
		this.setX((float) (this.length * Math.cos(angle)));
		this.setY((float) (this.length * Math.sin(angle)));
	}

	/**
	 * Takes an angle in Radians and returns the Angle in Degrees
	 * 
	 * @param angleRadians
	 * @return the same Angle in degrees
	 */
	private float calculateDegrees(float angleRadians) {
		return (float) (angleRadians * 57.2957795);
	}

	/**
	 * Takes an angle in Degrees and returns the Angle in Radians
	 * 
	 * @param angleDegrees
	 * @return the same Angle in radians
	 */
	private float calculateRadians(float angleDegrees) {
		return (float) (angleDegrees / 57.2957795);
	}

	/**
	 * Normalizes an angle to be between 0 and 2PI Radians
	 * 
	 * @param angle
	 *            the angle
	 * @return the angle normalized
	 */
	public static float normalizeAngle(float angle) {
		if (angle > Math.PI * 2) {
			return normalizeAngle((float) (angle - Math.PI * 2));
		} else if (angle < 0) {
			return normalizeAngle((float) (angle + Math.PI * 2));
		} else {
			return angle;
		}
	}

	/**
	 * Calculates the angle of an vector with the given coordinates
	 * 
	 * @param x
	 *            the x coordinate of an vector
	 * @param y
	 *            the z coordinate of an vector
	 * @return the angle between these coordinates
	 */
	public static float calculateAngle(float x, float y) {
		float result = (float) Math.atan(y / x); // atan = tan^-1
		if (x >= 0) {
			return result;
		} else {
			return (float) (result - Math.PI);
		}
	}

	@Override
	public MyVector2f clone() {
		return new MyVector2f(this.vector.getX(), this.vector.getY());
	}
}
