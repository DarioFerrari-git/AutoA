import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def compare_policies_per_net(what, comp_filename, networks=['4x4', '8x8', '16x16'],
                             input_dir="results/prob1", data_filename="aggregate.csv",
                             logx=False, logy=False, width = 0.25,
                             plot_target=0, output_dir="plots/prob1", legend_pos=0, dirs_override=None):
    # build data structure suitable for plot
    crossings = {}
    if dirs_override:
        dirs = dirs_override
    else:
        dirs = os.listdir(input_dir)
        dirs.sort()
    for f in dirs:
        #if "_d16_" not in f:  # filter out work in progress experiments
        print(f)
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
        crossings_p_sorted = sorted(crossings[p], key=lambda k: int(k.split('x')[0]), reverse=False)
        # print(crossings_p_sorted)
        crossings_vals_sorted = [crossings[p][k] for k in crossings_p_sorted]
        print(crossings_vals_sorted)
        if p == 'numPol':
            label = 'precedence'
        else:
            label = p
        bar = ax.bar(xXpol[p], crossings_vals_sorted, width, label=label, hatch=hatchXpol[p])
        ax.bar_label(bar, padding=3, fontsize=8)
    ax.set_xticks(x)
    ax.set_xticklabels(crossings_p_sorted)
    ax.legend(loc=legend_pos)

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")


def compare_breadth_per_net(what, comp_filename, networks=[4, 8, 16],
                            breadths=[1/2, 3/4, 1],
                             input_dir="results/prob1", data_filename="aggregate.csv",
                             logx=False, logy=False, width = 0.25,
                             plot_target=0, output_dir="plots/prob1"):
    # build data structure suitable for plot
    crossings = {}
    dirs = os.listdir(input_dir)
    dirs.sort()
    for f in dirs:
        if "altPol" in f:  # filter out irrelevant
            #print(f)
            data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
            for n in networks:
                net = f"{n}x{n}"
                #print(net)
                if net in f:
                    #print(f"\t{net} in {f}")
                    for alt_ratio in breadths:
                        #print(f"\t\t{alt_ratio}")
                        a = int(n * alt_ratio)
                        if f'a{a}' in f:
                            print(f)
                            #print(f"\t\ta{a} in {f}")
                            if alt_ratio not in crossings:
                                crossings[alt_ratio] = {}
                            crossings[alt_ratio][net] = data.loc[(data.shape[0] - 1), what]
    print(crossings)

    networks = crossings[breadths[0]].keys()
    x = np.arange(len(networks))  # the label locations
    # print(x)
    #width = 0.25  # the width of the bars
    policies = {breadths[0]: x - width / 2 - width / 2,
                breadths[1]: x,
                breadths[2]: x + width / 2 + width / 2}
    #print(policies.values())
    hatchXpol = {breadths[0]: "o",
                 breadths[1]: "/",
                 breadths[2]: "#"}  # modes: * + - . / O X \ o x |

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    #plt.figure()
    fig, ax = plt.subplots()
    ax.set_title(f"{what} per network size across no. of alt. routes")
    ax.set_ylabel(f"{what}")
    ax.set_xlabel("network size")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    for p in crossings:
        crossings_p_sorted = sorted(crossings[p], key=lambda k: int(k.split('x')[0]), reverse=False)
        #print(crossings_p_sorted)
        crossings_vals_sorted = [crossings[p][k] for k in crossings_p_sorted]
        print(crossings_vals_sorted)
        bar = ax.bar(policies[p], crossings_vals_sorted, width, label=f"# alt. routes = net. size * {p}",
                     hatch=hatchXpol[p])
        '''bar = ax.bar(policies[p], crossings[p].values(), width, label=f"# alt. routes = net. size * {p}",
                     hatch=hatchXpol[p])'''
        ax.bar_label(bar, padding=3, fontsize=8)
    ax.set_xticks(x)
    ax.set_xticklabels(crossings_p_sorted)
    ax.legend()

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")

