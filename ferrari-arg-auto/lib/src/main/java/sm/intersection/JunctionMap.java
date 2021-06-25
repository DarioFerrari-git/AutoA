package sm.intersection;

import sm.arg.intersection.FourWaysJunctionConfig;

public class JunctionMap {
	
	//private final Logger log = LoggerFactory.getLogger(JunctionMap.class);
	private final FourWaysJunctionConfig[][] junctions;
	private int n;
	private int m;
	
public JunctionMap(final int n,final int m, final FourWaysJunctionConfig[][] junctions){//,final FourWaysJunctionConfig bho0) {
this.n=n;
this.m=m;
this.junctions=junctions;
for(int i=0;i<n;i++) {
	for(int j=0;j<m;j++) {
		this.junctions[i][j]=new FourWaysJunctionConfig(null, null);
		}
	}
}
public String toString() {
	String str = "";
	for (int i = 0 ; i<this.n ; i ++ ){
        for (int j = 0 ; j < this.m ; j++){
            str += this.junctions[i][j]+"\t";
        }
        str += "\n";
    }
	return str;
}
public FourWaysJunctionConfig getJunction(int x,int y){ 
	return this.junctions[x][y]; }

public JunctionMap setJunction(int x,int y,FourWaysJunctionConfig fourWC){ 
	this.junctions[x][y]=fourWC; 
return this;}

}