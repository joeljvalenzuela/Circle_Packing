/**
 * Author: Joel Valenzuela
 * Date: September 30th, 2014
 * Version: 1.0
 *
 * Resources:
 *  MergeSort adapted from Resource:
 *  http://www.vogella.com/tutorials/JavaAlgorithmsMergesort/article.html
 */
package bubbles;

import java.util.*;
import bubbles.*;

public class MergeSort
{
       public static ArrayList<Bubble> numbers;
       public static ArrayList<Bubble> helper;
       public static int number;

  public static ArrayList<Bubble> sort(ArrayList<Bubble> values) {
    numbers = values;
    number = values.size();
    helper = new ArrayList<Bubble>();
    for(int i=0; i<number;i++)
        helper.add(new Bubble());
    mergesort(0, number - 1);
    return numbers;
  }

  public static void mergesort(int low, int high) {    
    if (low < high) {      
      int middle = low + (high - low) / 2;      
      mergesort(low, middle);      
      mergesort(middle + 1, high);      
      merge(low, middle, high);
    }
  }

  public static void merge(int low, int middle, int high) {

    for (int i = low; i <= high; i++) {
      helper.set(i, numbers.get(i));
    }

    int i = low;
    int j = middle + 1;
    int k = low;

    while (i <= middle && j <= high) {
        if(helper.get(i).diameter<=helper.get(j).diameter){
          numbers.set(k, helper.get(i));
        i++;
      } else {        
          numbers.set(k, helper.get(j));
        j++;
      }
      k++;
    }
    
    while (i <= middle) {
        numbers.set(k, helper.get(i));
        k++;
        i++;
    }

  }
 }