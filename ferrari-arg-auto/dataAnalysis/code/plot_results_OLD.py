import os
import numpy as np
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

#print(os.getcwd())

# SET THESE PARAMS
experiment = 4  # 1 = 2x2, 2 = 4x4, 3 = 8x8, 4 = 16x16
plot_target = 0  # 0 = show(), 1 = savefig()

input_dir = "../results"
data_filename = "performance.csv"
props_filename = "sim_settings.properties"
output_dir = "../plots"

if plot_target == 1:
    plt.ioff()  # non interactive: plot to file, not screen
    matplotlib.use("PDF")

# move ticks on right
plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

data = pd.read_csv(f'{input_dir}/{experiment}/{data_filename}')
#print(data.head())
with open(f'{input_dir}/{experiment}/{props_filename}') as p:
    grid = f'{p.readline().split("=")[1].strip()}x{p.readline().split("=")[1]}'.strip()
    #print(grid)

# PLOT 1.1
plt.figure()
ax1 = plt.subplot(2, 1, 2)  # 2nd first to put xlabel
if experiment == 1:
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 20), data["vehicles"], marker="o", color="b")
    plt.xticks(np.arange(0, 22, 2))
    plt.yticks(np.arange(0, 40, 5))
elif experiment == 2:
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 30), data["vehicles"], marker="o", color="b")
    plt.xticks(np.arange(0, 32, 2))
elif experiment == 3:
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 34), data["vehicles"], marker="o", color="b")
    plt.xticks(np.arange(0, 36, 2))
    plt.yticks(np.arange(0, 640, 100))
else:
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 38), data["vehicles"], marker="o", color="b")
    plt.xticks(np.arange(0, 40, 2))
plt.grid()
plt.ylabel("# vehicles")
plt.xlabel("simulation step")
# PLOT 1.2
ax2 = plt.subplot(2, 1, 1)  # 1st second to inherit xlabel
plt.setp(ax2.get_xticklabels(), visible=False)
plt.title(f"Performance per simulation step ({grid} network)")  # title in 1st plot
if experiment == 1:
    plt.setp(ax2.get_yticklabels(), visible=False)
    ax2.sharex(ax1)
    ax2.sharey(ax1)
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 20), data["alternative_routes_used"], marker="^", color="g")
elif experiment == 2:
    ax2.sharex(ax1)
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 30), data["alternative_routes_used"], marker="^", color="g")
    plt.yticks(np.arange(0, 700, 100))
elif experiment == 3:
    plt.setp(ax2.get_yticklabels(), visible=False)
    ax2.sharex(ax1)
    ax2.sharey(ax1)
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 34), data["alternative_routes_used"], marker="^", color="g")
else:
    ax2.sharex(ax1)
    plt.plot(data["simulation_step"].where(data["simulation_step"] <= 38), data["alternative_routes_used"], marker="^", color="g")
    plt.xticks(np.arange(0, 40, 2))
plt.grid()
plt.ylabel("# alt. routes")

if plot_target == 0:
    plt.show()
else:
    os.mkdir(f"{output_dir}/{grid}")
    plt.savefig(f"{output_dir}/{grid}/performance_steps.pdf")

# PLOT 2
plt.figure()
plt.plot(data["vehicles"], data["alternative_routes_used"], marker="x", color="r")
plt.grid()
plt.title(f"Performance per number of vehicles ({grid} network)")
plt.ylabel("# alt. routes")
plt.xlabel("# vehicles")
if experiment in [3, 4]:
    plt.figure()
    plt.plot(data["vehicles"], data["alternative_routes_used"], marker="x", color="r")
    plt.grid()
    plt.title(f"Performance per number of vehicles ({grid} network)")
    plt.ylabel("# alt. routes")
    plt.xlabel("# vehicles")
    plt.yscale("log")
    if plot_target == 1:
        plt.savefig(f"{output_dir}/{grid}/performance_vehicles_log.pdf")

if plot_target == 0:
    plt.show()
else:
    plt.yscale("linear")
    plt.savefig(f"{output_dir}/{grid}/performance_vehicles.pdf")

# OO interface (lower level, more flexibility)
#fig, ax = plt.subplots()  # Create a figure containing a single axes.
#fig, axs = plt.subplots(2, 2)  # 4 plots
#ax.plot(data["simulation_step"], data["alternative_routes_used"], label="# alt. routes", marker="x")  # Plot some data on the axes.
#ax.plot(data["simulation_step"], data["vehicles"], label="# vehicles", marker="o")
# OR PLT interface
