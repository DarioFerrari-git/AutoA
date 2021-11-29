import os
import numpy as np
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt

########## DOC SET THESE PARAMS ##########
plot_target = 0  # 0 = show(), 1 = savefig()

input_dir = "../results/prob1"
data_filename = "aggregate.csv"
output_dir = "../plots/prob1"
########################################

##### DOC config matplotlib
if plot_target == 1:
    plt.ioff()  # non interactive: plot to file, not screen
    matplotlib.use("PDF")

# move ticks on right
plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

#markers = ['o', '^', '8', 's', '*', '+', 'x']
#colors = ['b', 'g', 'r', 'c', 'm', 'y', 'k']

# colorblind friendly
plt.style.use('tableau-colorblind10')
##########

# TODO COMPARE served and slowdowns per grid size depending on # of alternate routes available

networks = [4, 8, 16]
fractions = [1/2, 3/4, 1]

# build data structure suitable for plot
crossings = {}
dirs = os.listdir(input_dir)
dirs.sort()
for f in dirs:
    if "altPol" in f:  # filter out irrelevant
        print(f)
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        for n in networks:
            net = f"{n}x{n}"
            #print(net)
            if net in f:
                print(f"\t{net} in {f}")
                #for a in altXnet[n]:
                for alt_ratio in fractions:
                    print(f"\t\t{alt_ratio}")
                    a = int(n*alt_ratio)
                    if f'a{a}' in f:
                        print(f"\t\ta{a} in {f}")
                        if alt_ratio not in crossings:
                            crossings[alt_ratio] = {}
                        crossings[alt_ratio][net] = data.loc[(data.shape[0]-1), 'crossings']
for k in crossings:
    print(k)
    for v in crossings[k]:
        print(f'\t{v}: {crossings[k][v]}')


networks = crossings[fractions[0]].keys()
x = np.arange(len(networks))  # the label locations
#print(x)
width = 0.25  # the width of the bars
policies = {fractions[0]: x - width/2 - width/2,
            fractions[1]: x,
            fractions[2]: x + width/2 + width/2}
print(policies.values())
hatchXpol = {fractions[0]: "o",
             fractions[1]: "/",
             fractions[2]: "#"}  # modes: * + - . / O X \ o x |

#plt.figure()
fig, ax = plt.subplots()
ax.set_title("Throughput per network size across no. of alt. routes")
ax.set_ylabel("throughput")
ax.set_xlabel("network size")
#plt.xscale("log")
plt.yscale("log")
for p in crossings:
    bar = ax.bar(policies[p], crossings[p].values(), width, label=f"# alt. routes = net. size * {p}", hatch=hatchXpol[p])
    ax.bar_label(bar, padding=3, fontsize=8)
ax.set_xticks(x)
ax.set_xticklabels(networks)
ax.legend()

fig.tight_layout()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-crossingsXnetwork-policy.pdf"  ##### CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")

########## DOC BAR PLOT NO. CROSSINGS (= SERVED) AGAINST NETWORK SIZE FOR EVERY POLICY (LEGENDA) ##########
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
ax.set_title("Served vehicles per network size across policies")
ax.set_ylabel("# served vehicles")
ax.set_xlabel("network size")
#plt.xscale("log")
#plt.yscale("log")
for p in crossings:
    bar = ax.bar(policies[p], crossings[p].values(), width, label=f"{p}")
    ax.bar_label(bar, padding=3, fontsize=8)
ax.set_xticks(x)
ax.set_xticklabels(networks)
ax.legend()

fig.tight_layout()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-crossingsXnetwork-policy.pdf"  ##### NB CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################

########## DOC BAR PLOT NO. SLOWDOWNS (= WAITINGS) AGAINST NETWORK SIZE FOR EVERY POLICY (LEGENDA) ##########
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
                crossings[tag][n] = data.loc[(data.shape[0]-1), 'waitings']
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
ax.set_title("Slowdowns per network size across policies")
ax.set_ylabel("# slowdowns")
ax.set_xlabel("network size")
#plt.xscale("log")
#plt.yscale("log")
for p in crossings:
    bar = ax.bar(policies[p], crossings[p].values(), width, label=f"{p}")
    ax.bar_label(bar, padding=3, fontsize=7)
ax.set_xticks(x)
ax.set_xticklabels(networks)
ax.legend()

fig.tight_layout()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-slowdownsXnetwork-policy.pdf"  ##### NB CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################

########## DOC LINE PLOT NO. CROSSINGS (= SERVED) AGAINST NO. VEHICLES FOR EVERY POLICY (LEGENDA) ##########
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
    comp_filename = "comparison-crossingsXvehicles-policy.pdf"  ##### NB CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################

########## DOC LINE PLOT NO. SLOWDOWNS (= WAITING) AGAINST NO. VEHICLES FOR EVERY POLICY (LEGENDA) ##########
plt.figure()
plt.grid()
plt.title("Slowdowns per no. of vehicles across policies")
plt.ylabel("# slowdowns")
plt.xlabel("# vehicles")
#plt.xscale("log")
#plt.yscale("log")
dirs = os.listdir(input_dir)
dirs.sort()
i = 0
for f in dirs:
    if "_d16_" not in f:  # filter out work in progress experiments
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        plt.plot(data["vehicles"], data["waitings"], label=f"{f.replace('r10_', '')}", #marker=markers[i], color=colors[i],
                 markersize=4)
        i += 1
plt.legend()

if plot_target == 0:
    plt.show()
else:
    if not os.path.exists(f"{output_dir}"):
        os.mkdir(f"{output_dir}")
    comp_filename = "comparison-slowdownsXvehicles-policy.pdf"  ##### NB CHANGE APPROPRIATELY
    plt.savefig(f"{output_dir}/{comp_filename}")
########################################
