import os
import numpy as np
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

# SET THESE PARAMS
plot_target = 1  # 0 = show(), 1 = savefig()

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

plt.figure()
plt.grid()
plt.title(f"Performance per number of vehicles across networks")
plt.ylabel("# alt. routes")
plt.xlabel("# vehicles")
plt.xscale("log")
plt.yscale("log")
dirs = os.listdir(input_dir)
dirs.sort()
for f in dirs:
    #print(f)
    data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
    #print(data.head())
    with open(f'{input_dir}/{f}/{props_filename}') as p:
        grid = f'{p.readline().split("=")[1].strip()}x{p.readline().split("=")[1]}'.strip()
        #print(grid)
    plt.plot(data["vehicles"], data["alternative_routes_used"], label=f"{grid} network")

plt.legend()

if plot_target == 0:
    plt.show()
else:
    plt.savefig(f"{output_dir}/performance_comparison.pdf")
