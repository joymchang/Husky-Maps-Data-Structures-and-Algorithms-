package seamfinding;

import seamfinding.energy.EnergyFunction;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code

        int height = picture.height();
        int width = picture.width();

        // step 1 - initialize 2d array
        double[][] arr = new double[width][height];
        int[][] parent = new int[width][height];

        // step 2 - fill out leftmost column with each pixel's energy
        for(int i = 0; i < height; i++) {
            arr[0][i] = f.apply(picture, 0, i);
            parent[0][i] = -1;
        }

        // step 3 - fill out remaining columns
        for(int i = 1; i < width; i++) {
            for(int j = 0; j < height; j++) {

                double prevMin = arr[i - 1][j];
                int bestPathY = j;

                if((j > 0) && (arr[i - 1][j - 1] < prevMin)) {
                    prevMin = arr[i - 1][j - 1];
                    bestPathY = j - 1;
                }

                if((j < height - 1) && (arr[i - 1][j + 1] < prevMin)) {
                    prevMin = arr[i - 1][j + 1];
                    bestPathY = j + 1;
                }

                arr[i][j] = f.apply(picture, i, j) + prevMin;
                parent[i][j] = bestPathY;
            }
        }

        // find the y-index in the rightmost column with the smallest cost
        int minY = 0;
        double minTotal = arr[width - 1][0];
        for(int j = 0; j < height; j++) {
            if(arr[width - 1][j] < minTotal) {
                minTotal = arr[width - 1][j];
                minY = j;
            }
        }

        // backtrack to the left 
        List<Integer> seam = new ArrayList<>();
        int currY = minY;

        for(int i = (width - 1); i >= 0; i--) {
            seam.add(currY);
            currY = parent[i][currY];
        }

        // reverse the result and return
        Collections.reverse(seam);
        return seam;

        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
