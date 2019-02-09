import java.util.ArrayList;

/**
 * This class represents an unsigned integer which is stored internally as a 
 * prime factorization. Some operations suspected to be faster on PrimeBase
 * representations than on values.
 *
 * @author Josh Orndorff
 */
 
public class PrimeBase {
  
  public static ArrayList<Integer> primes = new ArrayList<Integer>();
  
  // Static initializer
  static {
    // Initialize the list of primes. In the future it may be wise to generate
    // the first several with a Sieve of Eratosthenes, or something similar, but
    // for now I'll keep it like this so I can easily check the largest prime
    // that has been accessed so far.
    primes.add(2);
    primes.add(3);
  }
  
  // The underlying ArrayList of powers
  private ArrayList<PrimeBase> factorization; // null means  stale.
  private int value;                        // zero means stale.
  
  /**
   * Constructor for a simple identity number. Both value and factorization will
   * be fresh.
   */
  public PrimeBase() {
    value = 1;
    factorization = new ArrayList<PrimeBase>();
  }
  
  /**
   * Constructor that takes a value argument. Only value will be fresh.
   */
  public PrimeBase(int n) {
    value = n;
    factorization = null;
  }
  
  /**
   * Constructor that takes ArrayList of powers. Only factorization will be fresh.
   */
  public PrimeBase(ArrayList<PrimeBase> powers) {
    //TODO In the future I should figure out how make this work for any list-like object.
    factorization = powers;
    value = 0;
  }
  
  /**
   * Ensures the number's factorization is up-to-date by factoring when necessary.
   */
  private void updateFactorization() {
    // Do nothing if the factorization is already fresh.
    if (factorization != null)
      return;
    
    //TODO write facotring algorithm here.
  }
  
  /**
   * Ensure's the number's value is up-to-date by multiplying factors when nevessary.
   */
  private void updateValue() {
    // Do nothing if the value is already fresh.
    if (value != 0)
      return;
      
    // If we don't yet know enough prime values, find them.
    if (primes.size() < factorization.size()){
      
      //TODO Calculate more primes, probably using some kind of sieve.
    }
    
    // Now calculate the value    
    for (int i = 0; i < factorization.size(); i++) {
      value *= primes.get(i) ^ factorization.get(i).getValue();
    }
  }
  
  /**
   * Converts number into a print-ready String of the factorization exponents.
   * @return the String of exponents
   */
  public String toString() {
    updateValue();
    return "Value: " + value + ", Factorization: " + factorization.toString();
  }
  
  /**
   * Multiply by other number in place.
   * @param other The number by which this number is multiplied.
   */
  public void multiplyBy(PrimeBase other) {
    
    // This makes value stale
    value = 0;
    
    int otherHighest = other.highestFactor();
    
    // Ensure the underlying array is only resized once this method.
    factorization.ensureCapacity(otherHighest);
    
    // Loop through other factors and add them to own factors
    for (int i = 0; i < otherHighest; i++) {
      factorization.set(i, factorization.get(i) + other.getPower(i));
    }
  }
  
  /**
   * Raise this number to the power of another number in place.
   * @param pow the exponent to which the number is raised.
   */
  public void toPower(PrimeBase pow) {
    
    // This requires a fresh factorization and will make value stale.
    updateFactorization(); 
    value = 0;
    
    for (int i = 0; i < factorization.size(); i++)
      factorization.get(i).multiplyBy(pow);
  }
  
  /**
   * Add other number in place.
   * The best algorithm I know for this so far is converting to a place-value
   * system, adding and factoring back to PrimeBase.
   * @param other The number to be added to this number.
   */
  public void add(PrimeBase other) {
    
    /*
     This is a good candidate for overloading with a method that takes a
     primative int unless a better algorithm is discovered.
     */    
  }
  
  /**
   * Convert this number to a place-value representation.
   * @return A primative with the same value as this number.
   */
  public int getValue() {
    updateValue();
    return value;
  }
  
  /**
   * Calculates the ordinal of this number's highest prime factor, but not the
   * value of that prime factor.
   *
   * Implemented internally with the ArrayList's size.
   *
   * @return The ordinal of the highest prime factor.
   */   
  public int highestFactor() {
    return factorization.size();
  }
  
  /**
   * Returns the exponent on the nth prime factor.
   * @param n The ordinal of the prime
   * @return The exponent on the nth prime
   */
  public PrimeBase getPower(int n) {
    return factorization.get(n);
  }
  
  /**
   * Calculates the total dimensionality of the number which is the sum of all
   * of its powers. This datum is characteristic of the number insofar as it
   * represents the maximum number of dimensions in which a hyper-rectangle
   * whose n-volume is equal to the number's value can exist.
   * @return the number's dimensionality
   */
  public int getDimension() {
    int dim = 0;
    for (PrimeBase pow : factorization)
      dim += pow.getValue();
    return dim;
  }
  
  /**
   * Returns whether the number is the identity (one).
   * @return whether the number is the identity (one).
   */
  public boolean isIdentity(){
    return getDimension() == 0;
  }
  
  /**
   * Computes the GCF of this number and another number.
   * @return a PrimeBase instance representing the GCF.
   */
  public PrimeBase gcfWith(PrimeBase other) {
    ArrayList<PrimeBase> powers;
    
    //TODO Maybe ensure dimension here for performance considerations.
    int hf;
    if (highestFactor() > other.highestFactor())
      hf = highestFactor();
    else
      hf = other.highestFactor();
        
    for (int i = 0; i < hf; i++) {
      if (getPower(i) > other.getPower(i))
        powers.add(getPower(i));
      else
        powers.add(other.getPower(i));
    }
    
    return new PrimeBase(powers);
  }
  
}











