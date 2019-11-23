package tools;

import java.util.Random;

import engine.Render;

public class PerlinNoise {

	//explains math
	//https://www.cs.umd.edu/class/spring2018/cmsc425/Lects/lect12-1d-perlin.pdf	
	
	private int l;
	public float[] noise;
	public float[] mappedNoise;
	private Vector map;
    private Random generator;

	/**
	 * 
	 * @param length how many values of noise are created
	 * @param frequency is the length of the sample 
	 * @param octaves adds more detail
	 * @param map resize range
	 * @param seed sets noise to specific randNoise
	 */
	public PerlinNoise(int length, float frequency, float Persistence, float octaveScale, int NumOfOctaves, Vector map, int seed) {
		l = length;
		this.map = map;
		noise = new float[l];
		mappedNoise = new float[l];
		//sets seed to get same noise every time with same seed
		generator = new Random(seed);
		//caps values at 1
		if(NumOfOctaves < 1) {
			NumOfOctaves = 1;
		}
		if(Persistence <= 0 || Persistence > 1) {
			Persistence = 1;
		}
		
		generatePerlinNoise(frequency, Persistence, octaveScale, NumOfOctaves);
		//noise(l, frequency);
	}
	
	private float[] noise(int length, float frequency) {
		float[] noise = new float[length];
 		int numOfLatticePoints = (int) (length*frequency);//Calculates the number of lattice points noise
		float[] randNoise = generateRandomNoise(numOfLatticePoints);//get random noise
		for(int i = 0; i <  length; i++) {
			float t = i * frequency;//convert [0, len] - [0,max lattice] with 1/f point in between range
			int tMin = (int) t % numOfLatticePoints;//lower lattice point
			int tMax = (tMin + 1) % numOfLatticePoints;// upper lattice point
			float alpha = t % 1;//gets t mod 1
			//interpolate at alpha
			//System.out.println(randNoise[tMin] + " " + randNoise[tMax]);
			//System.out.println(tMin + " " + tMax);
			noise[i] = smoothStep(randNoise[tMin], randNoise[tMax], alpha);
		}
		return noise;
	}

	private void generatePerlinNoise(float frequency, float Persistence, float octaveScale, int NumOfOctaves) {
		int max = 0;
		for(int octave = 0; octave < NumOfOctaves; octave++) {
			float tempfrequency = (float) Math.pow(octaveScale, -octave);
			float[] tempNoise = noise(l, frequency);
			for(int i = 0; i < l; i++) {
				noise[i] += Math.pow(Persistence, octave) * tempNoise[i];
			}
			max += Math.pow(Persistence, octave);
		}
		mappedNoise = getNoise(0, max);
	}
	
	/**
	 * does a smooth interpolation on aplha
	 */
	public float smoothStep(float y0, float y1, float alpha) {
		float tSmoothStep = alpha * alpha * (3 - 2 * alpha);//Kin Perlin's equation
		//float tSmoothStep = (float) ((1 - Math.cos(t * Math.PI)) * 0.5); 
		return lerp(y0, y1, tSmoothStep);
	}
	
	/**
	 * does a cosine interpolation on alpha
	 */
	public float cerp(float y0, float y1, float alpha) {
		float tSmoothStep = (float) ((1 - Math.cos(alpha * Math.PI)) * 0.5); 
		return lerp(y0, y1, tSmoothStep);
	}

	
	/**
	 * linear interpolation
	 * calculates the noise at alpha:[0,1]
	 * and at 0 noise is y0 and at 1 noise is y1
	 */
	public float lerp(float y0, float y1, float alpha) {
		//y = (b-a)/(1-0) (t - 1) + b
		return (1-alpha)*y0 + alpha*y1;
	}
	
	/**
	 * generates random noise and returns array with noise
	 * noise is made with random function
	 * f(0) = f(n)
	 */
	private float[] generateRandomNoise(int length) {
		float[] tempNoise = new float[length];//temp array created
		for(int i = 0; i < length - 1; i++) {
			tempNoise[i] = generator.nextFloat();//noise is made
		}
		tempNoise[length-1] = tempNoise[0];
		return tempNoise;//noise is returned
	}
	
	/**
	 * returns the noise mapped to map
	 */
	public float[] getNoise(float min, float max) {
		float[] tempMappedNoise = noise;
		for(int x = 0; x < this.l; x++) {
			tempMappedNoise[x] = Render.map(noise[x], min, max, map.getX(), map.getY());
		}
		return tempMappedNoise;
	}

	/**
	 *gets noise at x and maps it to the map
	 */
	public float noise(int x) {
		//uses map function in render class
		//noise is [0,1] and that gets mapped to [x,y] map
		return Render.map(noise[x], 0, 1, map.getX(), map.getY());
	}
	
}
