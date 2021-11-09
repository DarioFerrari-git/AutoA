import os
import numpy as np
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

#markers = ['o', '^', '8', 's', '*', '+', 'x']
#colors = ['b', 'g', 'r', 'c', 'm', 'y', 'k']
##########

########## BAR PLOT NO. CROSSINGS (= SERVED) AGAINST NETWORK SIZE FOR EVERY POLICY (LEGENDA) ##########
networks = ['4x4', '8x8', '16x16']

# build data structure suitable for plot
crossings = {}
dirs = os.listdir(input_dir)
dirs.sort()
for f in dirs:
    if "_d16_" not in f:  # filter out work in progress experiments
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        tag = f.split('_')[-1]
        if tag not in crossings:
            crossings[tag] = {}
        for n in networks:
            if n in f:
                crossings[tag][n] = data.loc[(data.shape[0]-1), 'crossings']
print(crossings)

networks = crossings['altPol'].keys()
x = np.arange(len(networks))  # the label locations
#print(x)
width = 0.25  # the width of the bars
policies = {'altPol': x - width/2 - width/2,
            'numPol': x,
            'urgPol': x + width/2 + width/2}
#print(policies.values())

#plt.figure()
fig, ax = plt.subplots()
#plt.grid()
ax.set_title("Served vehicles per network size across policies")
ax.set_ylabel("# served vehicles")
ax.set_xlabel("network size")
#plt.xscale("log")
#plt.yscale("log")
#dirs = os.listdir(input_dir)
#dirs.sort()
for p in crossings:
    #if "_d16_" not in f:  # filter out work in progress experiments
        #data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        #for n in crossings[p]:
            #if n in f:
                #tag = f.split('_')[-1]
                #print(tag)
                bar = ax.bar(policies[p], crossings[p].values(), width, label=f"{p}")
                ax.bar_label(bar, padding=3)
ax.set_xticks(x)
ax.set_xticklabels(networks)
ax.legend()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-crossingsXnetwork-policy.pdf"  ##### CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################

########## LINE PLOT NO. CROSSINGS (= SERVED) AGAINST NO. VEHICLES FOR EVERY POLICY (LEGENDA) ##########
plt.figure()
plt.grid()
plt.title("Served vehicles per no. of vehicles across policies")
plt.ylabel("# served vehicles")
plt.xlabel("# vehicles")
#plt.xscale("log")
#plt.yscale("log")
dirs = os.listdir(input_dir)
dirs.sort()
i = 0
for f in dirs:
    if "_d16_" not in f:  # filter out work in progress experiments
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        plt.plot(data["vehicles"], data["crossings"], label=f"{f.replace('r10_', '')}", #marker=markers[i], color=colors[i],
                 markersize=4)
        i += 1
plt.legend()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-crossingsXvehicles-policy.pdf"  ##### CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################
