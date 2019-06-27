

/*
 * This is not safe to use yet, runtime errors possible
 * when applying wrong amount of inputs or whatever
 */

class Parser {

  public Operation getOperator(String[] input) { // possibly more operators at once
    if (input[0].toLowerCase().equals("-ps")) {
      return new PixelSort(Integer.parseInt(input[1]), input[2]);
      
    } else {

      // Add other possible operators

    }

    return new Operation();
  }

  public String getFileName(String[] input) {
    return input[input.length - 1];
  }


}
