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
##########

markers = ['o', '^', 'x', '*']
colors = ['b', 'g', 'r', 'k']

# ACROSS NETWORKS
plt.figure()
plt.grid()
plt.title(f"Alt. routes per no. of vehicles across networks")
plt.ylabel("# alt. routes")
plt.xlabel("# vehicles")
plt.xscale("log")
plt.yscale("log")
#dirs = ["r10_2x2_d2_a2", "r10_4x4_d4_a2", "r10_8x8_d8_a4", "r10_16x16_d16_a8"]  # manual selection
dirs = ["r10_2x2_d4_a4", "r10_4x4_d4_a4", "r10_8x8_d8_a4", "r10_16x16_d16_a8"]  # alt version
i = 0
for f in dirs:
    data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
    plt.plot(data["vehicles"], data["alternative_routes_used"], label=f"{f.replace('r10_', '')}", marker=markers[i], color=colors[i], markersize=4)
    i += 1
plt.legend()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = f"comparison-networks-alt.pdf"  ##### CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")

# DEPTH AND BREADTH
grid = "16x16"  ##### CHANGE APPROPRIATELY

plt.figure()
plt.grid()
plt.title(f"Alt. routes per no. of vehicles across depth and breadth ({grid} network)")
plt.ylabel("# alt. routes")
plt.xlabel("# vehicles")
#plt.xscale("log")
plt.yscale("log")
dirs = os.listdir(input_dir)
dirs.sort()
i = 0
for f in dirs:
    if f"r10_{grid}_" in f:  # first part filters out old experiments
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        plt.plot(data["vehicles"], data["alternative_routes_used"], label=f"{f.replace(f'r10_{grid}_', '')}", marker=markers[i], color=colors[i], markersize=4)
        i += 1
plt.legend()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = f"comparison-{grid}-depth_breadth-log.pdf"  ##### CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
