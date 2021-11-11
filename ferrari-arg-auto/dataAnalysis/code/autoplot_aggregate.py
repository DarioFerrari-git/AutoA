import os
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

########## SET THESE PARAMS ##########
plot_target = 0  # 0 = show(), 1 = savefig()

input_dir = "../results/prob1"
data_filename = "aggregate.csv"
output_dir = "../plots/prob1"
########################################

##### config matplotlib
if plot_target == 1:
    plt.ioff()  # non interactive: plot to file, not screen
    matplotlib.use("PDF")

# move ticks on right
plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

# colorblind friendly
plt.style.use('tableau-colorblind10')
##########

##### do the plots
# for each experiment
dirs = os.listdir(input_dir)
dirs.sort()
for d in dirs:
    dir_path = f"{input_dir}/{d}"
    if os.path.isdir(dir_path) and d not in ["1", "2", "3", "4"]:  # skip old experiments, kept for backup
        #print(d)
        data = pd.read_csv(f'{dir_path}/{data_filename}')

        # PLOT 1.1
        plt.figure()
        ax1 = plt.subplot(2, 1, 2)  # 2nd first to put xlabel
        plt.plot(data["simulation_step"], data["vehicles"], marker="o", color="b")
        plt.grid()
        plt.ylabel("# vehicles")
        plt.xlabel("simulation step")

        # PLOT 1.2
        ax2 = plt.subplot(2, 1, 1)  # 1st second to inherit xlabel
        plt.setp(ax2.get_xticklabels(), visible=False)
        plt.title(f"Performance per simulation step (params: {d})")  # title in 1st plot
        ax2.sharex(ax1)
        plt.plot(data["simulation_step"], data["alternative_routes_used"], marker="^", color="g")
        plt.grid()
        plt.ylabel("# alt. routes")

        # show or save?
        if plot_target == 0:
            plt.show()
        else:
            if not os.path.exists(f"{output_dir}/{d}"):
                os.mkdir(f"{output_dir}/{d}")
            if not os.path.exists(f"{output_dir}/{d}/performance_steps.pdf"):
                plt.savefig(f"{output_dir}/{d}/performance_steps.pdf")

        # PLOT 2
        plt.figure()
        plt.plot(data["vehicles"], data["alternative_routes_used"], marker="x", color="r")
        plt.grid()
        plt.title(f"Performance per number of vehicles ({d} network)")
        plt.ylabel("# alt. routes")
        plt.xlabel("# vehicles")

        # show or save?
        if plot_target == 0:
            plt.show()
        else:
            if not os.path.exists(f"{output_dir}/{d}"):
                os.mkdirs(f"{output_dir}/{d}")
            if not os.path.exists(f"{output_dir}/{d}/performance_vehicles.pdf"):
                plt.savefig(f"{output_dir}/{d}/performance_vehicles.pdf")
##########
