import os
import numpy as np
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

plt.ioff()  # non interactive: plot to file, not screen
matplotlib.use("PDF")

#print(os.getcwd())

input_dir = "../results"
data_filename = "performance.csv"
props_filename = "sim_settings.properties"
output_dir = "../plots"

i = 0
for f in os.listdir(input_dir):
    data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
    #print(data.head())
    with open(f'{input_dir}/{f}/{props_filename}') as p:
        grid = f'{p.readline().split("=")[1].strip()}x{p.readline().split("=")[1]}'
        #print(grid)
    plt.figure(i)
    ax1 = plt.subplot(2, 1, 2)  # 2nd first to put xlabel
    plt.plot(data["simulation_step"], data["vehicles"], marker="o", color="b")
    plt.grid()
    #plt.xticks(np.arange(0, 50, 5))
    #plt.yticks(np.arange(0, 45, 5))
    plt.ylabel("# vehicles")
    plt.xlabel("simulation step")
    ax2 = plt.subplot(2, 1, 1, sharex=ax1)  # 1st second to inherit xlabel
    plt.setp(ax2.get_xticklabels(), visible=False)
    plt.title(f"Performance per simulation step ({grid} network)", fontsize=10)  # title in 1st plot
    plt.plot(data["simulation_step"], data["alternative_routes_used"], marker="^", color="g")
    plt.grid()
    #plt.yticks(np.arange(0, 40, 5))
    plt.ylabel("# alt. routes")

    # plt.show()
    os.mkdir(f"{output_dir}/{grid}")
    plt.savefig(f"{output_dir}/{grid}/performance_steps.pdf")

    plt.figure(2)
    plt.plot(data["vehicles"], data["alternative_routes_used"], marker="x", color="r")
    plt.grid()
    plt.title(f"Performance per number of vehicles ({grid} network)", fontsize=10)
    plt.ylabel("# alt. routes")
    plt.xlabel("# vehicles")

    # plt.show()
    plt.savefig(f"{output_dir}/{grid}/performance_vehicles.pdf")

# OO interface (lower level, more flexibility)
#fig, ax = plt.subplots()  # Create a figure containing a single axes.
#fig, axs = plt.subplots(2, 2)  # 4 plots
#ax.plot(data["simulation_step"], data["alternative_routes_used"], label="# alt. routes", marker="x")  # Plot some data on the axes.
#ax.plot(data["simulation_step"], data["vehicles"], label="# vehicles", marker="o")
# OR PLT interface
