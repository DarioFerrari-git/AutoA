import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def compare_policies_per_net(what, comp_filename, networks = ['4x4', '8x8', '16x16'],
                             input_dir="results/prob1", data_filename="aggregate.csv",
                             logx=False, logy=False, width = 0.25,
                             plot_target=0, output_dir="plots/prob1"):
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
                    crossings[tag][n] = data.loc[(data.shape[0]-1), what]
    print(crossings)

    networks = crossings['altPol'].keys()
    x = np.arange(len(networks))  # the label locations
    #print(x)
    xXpol = {'altPol': x - width/2 - width/2,
                'numPol': x,
                'urgPol': x + width/2 + width/2}
    #print(policies.values())
    hatchXpol = {'altPol': "o",
                'numPol': "/",
                'urgPol': "#"}  # modes: * + - . / O X \ o x |

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    #plt.figure()
    fig, ax = plt.subplots()
    ax.set_title(f"{what} per network size across policies")
    ax.set_ylabel(f"# {what}")
    ax.set_xlabel("network size")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    for p in crossings:
        bar = ax.bar(xXpol[p], crossings[p].values(), width, label=f"{p}", hatch=hatchXpol[p])
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
        plt.savefig(f"{output_dir}/{comp_filename}")


compare_policies_per_net("crossings", "comparison-crossingsXnetwork-policy.pdf", ["4x4", "8x8", "16x16"], plot_target=0)
compare_policies_per_net("waitings", "comparison-slowdownsXnetwork-policy.pdf", ["4x4", "8x8", "16x16"], plot_target=0)


def compare_breadth_per_net(what, comp_filename, networks=['4x4', '8x8', '16x16'],
                            breadths=['a2','a3','a4','a5','a6','a7','a8','a9','a10','a11','a12'],
                             input_dir="results/prob1", data_filename="aggregate.csv",
                             logx=False, logy=False, width = 0.25,
                             plot_target=0, output_dir="plots/prob1"):
    # build data structure suitable for plot
    crossings = {}
    dirs = os.listdir(input_dir)
    dirs.sort()
    for f in dirs:
        if "_d16_" not in f:  # filter out work in progress experiments
            data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
            for b in breadths:
                if b in f:
                    crossings[b] = {}
            for n in networks:
                if n in f:
                    crossings[b][n] = data.loc[(data.shape[0]-1), what]
    print(crossings)

    networks = crossings.keys()[0].keys()
    x = np.arange(len(networks))  # the label locations
    #print(x)
    actual_breadths = {}
    for b in crossings.keys():  # TODO how to dynamically adjust depending on # of keys?
        actual_breadths = {'altPol': x - width/2 - width/2,
                'numPol': x,
                'urgPol': x + width/2 + width/2}
    #print(policies.values())

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    #plt.figure()
    fig, ax = plt.subplots()
    ax.set_title(f"{what} per network size across policies")
    ax.set_ylabel(f"# {what}")
    ax.set_xlabel("network size")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
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
        plt.savefig(f"{output_dir}/{comp_filename}")


