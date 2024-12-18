import java.util.HashMap;

public class Heuristics {
    // Return an unassigned node with the minimum remaining value
    Field getHeuristicNode(Field[][] fields) {
        // minimum size start from 1 because all nodes with 0 values in domain are assigned
        for (int size = 1; size <= 9; size++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (fields[i][j].getDomainSize() == size) {
                        return fields[i][j];
                    }
                }
            }
        }
        return null;
    }

    // assign the selected node with the least constraining value
    int getHeuristicValue(Field field) {
        HashMap<Integer, Integer> countMap = new HashMap<Integer,Integer>();
        for (int value : field.getDomain()) {
            int count = 0;
            for (Field n : field.getNeighbours()) {
                count += n.getDomainSize();
                if (n.getDomain().contains(value)) {
                    count--;
                }
            }
            countMap.put(value, count);
        }

        int key = 0;
        int maxCount = -1; // maxCount may be 0
        for (HashMap.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                key = entry.getKey();
            }
        }
        return key;
    }
}
