/**
 * 
 */
package sm.paper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sm.arg.intersection.AltRoutesPolicy;
import sm.arg.intersection.DistanceRSU;
import sm.arg.intersection.FourWaysJunctionConfig;
import sm.arg.intersection.NumArgsPolicy;
import sm.intersection.BaseRSU;
import sm.intersection.CrossingPolicy;
import sm.intersection.JunctionMatrix;
import sm.intersection.SmartJunction;
import sm.intersection.sim.DeepAltRouteConfStrategy;
import sm.intersection.sim.DeepAltRouteRandomStrategy;
import sm.intersection.sim.Defaults;
import sm.intersection.sim.MultiJunctionAutoSimulation;
import sm.intersection.sim.Simulation;
import sm.intersection.sim.SingleJunctionAutoSimulation;
import sm.intersection.sim.VehiclesGenStrategy;

/**
 * @author sm
 *
 */
public final class Experiment01 {
    
    private static final String POLICY_P = "policy";

    private static final String STRAT_P = "strat";

    private static final Logger log = LoggerFactory.getLogger(Experiment01.class);

    private static final String MAX_STEPS_P = "maxSteps";
    private static final String GEN_STEPS_P = "genSteps";
    private static final String COLS_P = "cols";
    private static final String ROWS_P = "rows";
    private static final int GEN_X_S = 1;
    private static final int SEED = 42;
    private static final int SIM_STEP = 1;
    private static final int RSU_CONFIDENCE = 1;
    private static final int RSU_DISTANCE = 2 * Defaults.SAFETY_DISTANCE_SOFT;

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String simConfigPath = rootPath + "sim_settings.properties";
        Properties simProps = new Properties();
        simProps.load(new FileInputStream(simConfigPath));
        VehiclesGenStrategy strat = null;
        if ("DeepAltRouteRandomStrategy".equals(simProps.getProperty(STRAT_P))) {
            strat = new DeepAltRouteRandomStrategy();
        } else if ("DeepAltRouteConfStrategy".equals(simProps.getProperty(STRAT_P))) {
            strat = new DeepAltRouteConfStrategy();
        } else {
            log.error("UNSUPPORTED STRATEGY: choose <DeepAltRouteRandomStrategy> or <DeepAltRouteConfStrategy>");
            System.exit(-1);
        }
        CrossingPolicy pol = null;
        if ("AltRoutesPolicy".equals(simProps.getProperty(POLICY_P))) {
            pol = new AltRoutesPolicy("alt_routes");
        } else if ("NumArgsPolicy".equals(simProps.getProperty(POLICY_P))) {
            pol = new NumArgsPolicy("num_args");
        } else {
            log.error("UNSUPPORTED POLICY: choose <AltRoutesPolicy> or <NumArgsPolicy>");
            System.exit(-1);
        }

        SmartJunction[][] junctions = new SmartJunction[Integer.parseInt(simProps.getProperty(ROWS_P))][Integer
                .parseInt(simProps.getProperty(COLS_P))];
        FourWaysJunctionConfig j4;
        List<Simulation> simulations = new ArrayList<>();
        strat.setSeed(SEED);
        SingleJunctionAutoSimulation s;
        for (int r = 0; r < junctions.length; r++) {
            for (int c = 0; c < junctions[r].length; c++) {
                j4 = new FourWaysJunctionConfig(String.format("J_%d_%d", r, c), pol, 
                        new DistanceRSU(new BaseRSU("distance", RSU_CONFIDENCE), RSU_DISTANCE));
                junctions[r][c] = j4.getJunction();
                strat.configJunction(junctions[r][c]);
                s = new SingleJunctionAutoSimulation(junctions[r][c], GEN_X_S,
                        Integer.parseInt(simProps.getProperty(GEN_STEPS_P)),
                        Integer.parseInt(simProps.getProperty(MAX_STEPS_P)), strat, SIM_STEP);
                simulations.add(s);
            }
        }
        JunctionMatrix network = new JunctionMatrix(junctions);
        MultiJunctionAutoSimulation sim = new MultiJunctionAutoSimulation(network, simulations);
        sim.go(true);
        log.info("Props: {}", simProps.values());
    }

}
