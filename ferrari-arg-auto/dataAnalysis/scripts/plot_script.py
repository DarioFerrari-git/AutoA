from scripts.line_plots_utils import *
from scripts.bar_plots_utils import *

input_dir = "results/prob1"
output_dir = "plots/prob1/FZ"
plot_target = 0

# DOC additional for FZ
networks=[4, 8, 16]
### FIG 7
compare_2_metrics_per_net("alternative_routes_used", "simulation_step", "comparison-altRoutesXsteps-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target,
                          dirs_override=[f"r10_{n}x{n}_d{n}_a{int(n/2)}_altPol".replace('2', '3') for n in networks])
#########
compare_2_metrics_per_net("alternative_routes_used", "simulation_time", "comparison-altRoutesXtime-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limit=None)
compare_2_metrics_per_net("alternative_routes_used", "simulation_time", "comparison-altRoutesXtime-networks-ZOOM.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limit=0.125e6)


compare_2_metrics_per_net("crossings", "simulation_time", "comparison-crossingsXtime-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_net("crossings", "simulation_time", "comparison-crossingsXtime-networks-ZOOM.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limit=0.125e6)
compare_2_metrics_per_net("waitings", "simulation_time", "comparison-waitingsXtime-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)


compare_2_metrics_per_net_baseline("crossings", "simulation_time", "comparison-crossingsXtime-networks-withbaseline.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_net_baseline("crossings", "simulation_time", "comparison-crossingsXtime-networks-withbaseline-ZOOM1.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=1e6)
compare_2_metrics_per_net_baseline("crossings", "simulation_time", "comparison-crossingsXtime-networks-withbaseline-ZOOM2.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=0.05e6)

compare_2_metrics_per_net_baseline("waitings", "simulation_time", "comparison-waitingsXtime-networks-withbaseline.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_net_baseline("waitings", "simulation_time", "comparison-waitingsXtime-networks-withbaseline-ZOOM1.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=2e6)
compare_2_metrics_per_net_baseline("waitings", "simulation_time", "comparison-waitingsXtime-networks-withbaseline-ZOOM2.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=0.05e6)
compare_2_metrics_per_net_baseline("waitings", "simulation_step", "comparison-waitingsXstep-networks-withbaseline.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_net_baseline("waitings", "simulation_step", "comparison-waitingsXstep-networks-withbaseline-ZOOM1.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limity=100000)


### FIG 8
compare_2_metrics_per_breadth("crossings", "simulation_step", "comparison-crossingsXtime-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
#########
compare_2_metrics_per_breadth("crossings", "simulation_time", "comparison-crossingsXtime-altRoutes-ZOOM1.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limit=0.25e6)
compare_2_metrics_per_breadth("crossings", "simulation_time", "comparison-crossingsXtime-altRoutes-ZOOM2.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limit=0.05e6)
'''compare_2_metrics_per_breadth("waitings", "simulation_time", "comparison-waitingXtime-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_breadth("waitings", "simulation_time", "comparison-waitingXtime-altRoutes-ZOOM1.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=0.25e6)
compare_2_metrics_per_breadth("waitings", "simulation_time", "comparison-waitingXtime-altRoutes-ZOOM2.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=0.05e6)'''
compare_2_metrics_per_breadth("waitings", "simulation_step", "comparison-waitingXstep-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target)
compare_2_metrics_per_breadth("waitings", "simulation_step", "comparison-waitingXstep-altRoutes-ZOOMxy.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=False,
                              plot_target=plot_target, limitx=56, limity=15000)


compare_2_metrics_per_breadth_scatter("crossings", "alternative_routes_used", "comparison-crossingsXaltRoutes-altRoutes-scatter.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=False,
                              plot_target=plot_target, size=150, arrowc='black')
compare_2_metrics_per_breadth_scatter("waitings", "alternative_routes_used", "comparison-waitingsXaltRoutes-altRoutes-scatter.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=False, logx=True,
                              plot_target=plot_target, size=150, arrowc='black')
########################

# TODO I would remove these from paper...
compare_2_metrics_per_net("alternative_routes_used", "vehicles", "comparison-altRoutesXvehicles-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
compare_2_metrics_per_net("crossings", "alternative_routes_used", "comparison-crossingsXalternative_routes_used-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
compare_2_metrics_per_net("waitings", "alternative_routes_used", "comparison-slowdownsXalternative_routes_used-networks.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)


compare_2_metrics_per_breadth("crossings", "alternative_routes_used", "comparison-crossingsXaltRoutes-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
compare_2_metrics_per_breadth("waitings", "alternative_routes_used", "comparison-slowdownsXaltRoutes-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
compare_2_metrics_per_breadth("crossings", "vehicles", "comparison-crossingsXvehicles-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
compare_2_metrics_per_breadth("waitings", "vehicles", "comparison-slowdownsXvehicles-altRoutes.pdf",
                              input_dir=input_dir, output_dir=output_dir, logy=True, logx=True,
                              plot_target=plot_target)
#########################################


compare_policies_per_net("crossings", "comparison-crossingsXnetwork-policy.pdf",
                         input_dir=input_dir, output_dir=output_dir,
                         plot_target=plot_target, legend_pos=2)
compare_policies_per_net("waitings", "comparison-slowdownsXnetwork-policy.pdf",
                         input_dir=input_dir, output_dir=output_dir,
                         plot_target=plot_target)

compare_breadth_per_net("crossings", "comparison-crossingsXnetwork-breadth.pdf",
                        input_dir=input_dir, output_dir=output_dir,
                        plot_target=plot_target)
compare_breadth_per_net("waitings", "comparison-slowdownsXnetwork-breadth.pdf",
                        input_dir=input_dir, output_dir=output_dir,
                        plot_target=plot_target)
