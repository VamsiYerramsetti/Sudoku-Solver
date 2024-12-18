import java.util.ArrayList;
import java.util.PriorityQueue;

public class AC3 {
    class Arc{
        Field x;
        Field y;

        Arc(Field f1, Field f2){x = f1; y=  f2;}
    }

    //List of all arcs that we wish to prove consistent or not
    private ArrayList<Arc> arcs = new ArrayList<>();
    private Field[][] fields;

    int numArcs = 0;

    AC3(Field[][] fields){
        this.fields = fields;
        setupArcs();
    }

    //Returns true if all constraints are satisfied, false otherwise
    public boolean solve(){
        while(!arcs.isEmpty()){
            Arc arc = arcs.remove(0);
            // System.out.println(arc.x.getDomain());
            if(arcReduce(arc)){
                if(arc.x.getDomainSize()==0) return false;
                for(Field neighbour : arc.x.getOtherNeighbours(arc.y)){
                    arcs.add(new Arc(neighbour, arc.x));
                }
            }
        }
        System.out.println("Arcs used: " + numArcs);
        return true;
    }

    //Sets up all arcs
    void setupArcs(){
        //For each field, go through all it's neighbours and add an arc between the field and the neighbour
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                Field field = fields[i][j];
                for (Field neighbour : field.getNeighbours()) {
                    if(neighbour.getDomainSize()==0 ) continue;
                    arcs.add(new Arc(field, neighbour));
                }
            }
        }
    }

    private boolean arcReduce(Arc arc){
        numArcs++;
        boolean change = false;
        ArrayList<Integer> domain = new ArrayList<>(arc.x.getDomain());
        for(Integer vx : domain){
            boolean hasDifferentValue = false;
            for(Integer vy : arc.y.getDomain()){
                if(vx != vy){
                    hasDifferentValue = true;
                    break;
                }
            }

            if(!hasDifferentValue){
                arc.x.removeFromDomain(vx);
                change = true;
            }
        }
        return change;
    }
}
