import os
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

########## SET THESE PARAMS ##########
plot_target = 0  # 0 = show(), 1 = savefig()

input_dir = "../results"
data_filename = "aggregate.csv"
output_dir = "../plots"
########################################

##### config matplotlib
if plot_target == 1:
    plt.ioff()  # non interactive: plot to file, not screen
    matplotlib.use("PDF")

# move ticks on right
plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True
##########

plt.figure()
plt.grid()
plt.title(f"Performance per number of vehicles across networks")
plt.ylabel("# alt. routes")
plt.xlabel("# vehicles")
plt.xscale("log")
plt.yscale("log")
dirs = ["r10_2x2_d4_a4", "r10_4x4_d4_a4", "r10_8x8_d8_a4", "r10_16x16_d16_a8"]  # manual selection
for f in dirs:
    data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
    plt.plot(data["vehicles"], data["alternative_routes_used"], label=f"params: {f}")
plt.legend()

if plot_target == 1:
    plt.show()
else:
    comp_filename = f"comparison-{'-'.join(dirs)}.pdf"
    plt.savefig(f"{output_dir}/{comp_filename}")
