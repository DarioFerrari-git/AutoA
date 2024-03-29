import os
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.lines import Line2D
import matplotlib as mpl
import numpy as np
from adjustText import adjust_text


def compare_2_metrics_per_breadth(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
                                  plot_target=0, output_dir="plots/prob1", limitx=None, limity=None):

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    markers = list(Line2D.markers.keys())
    #markers = ['o', '^', '8', 's', '*', '+', 'x']

    fig = plt.figure()
    plt.grid()
    plt.title(f"{what} per {against} across no. of alt. routes")
    plt.ylabel(f"# {what}")
    plt.xlabel(f"# {against}")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    dirs = os.listdir(input_dir)
    dirs.sort()
    i = 0
    for f in dirs:
        if ("altPol" in f) or ("numPol" in f) and "16x16_d8" not in f and "d16_a4" not in f:
            data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
            x = data[against]
            y = data[what]
            if not limitx and not limity:
                plt.plot(x, y, label=f"{f.replace('r10_', '')}", marker=markers[i], markersize=4)
            else:
                if limitx:
                    x = data[against].where(data[against] <= limitx)
                    ticks = np.arange(0, limitx, limitx / 10)
                    if 'time' in against:
                        plt.xticks(ticks, [f'{x / 1000}' for x in ticks])  # DOC simulation time in seconds
                if limity:
                    y = data[what].where(data[what] <= limity)
                plt.plot(x, y, label = f"{f.replace('r10_', '')}", marker = markers[i], markersize = 4)
            i += 1
    plt.legend()

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")
    return plt


def compare_2_metrics_per_breadth_subplots(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
                                  plot_target=0, output_dir="plots/prob1", limitx=None, limity=None, nets=[4, 8, 16], figsize=(1, 1)):

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    markers = list(Line2D.markers.keys())
    #markers = ['o', '^', '8', 's', '*', '+', 'x']

    fig = plt.figure(figsize=(mpl.rcParams["figure.figsize"][0]*figsize[0], mpl.rcParams["figure.figsize"][1]*figsize[1]))
    dirs = os.listdir(input_dir)
    dirs.sort()
    print(f'dirs.sort(): {dirs}')
    dirs = sorted(dirs, key=lambda f: int(f.split('_')[3].replace('a', '')))  # '_a' param
    print(f'sorted(dirs): {dirs}')
    #i = 0
    r = 1
    for n in nets:
        i = 0
        print(f'{n}')
        ax = plt.subplot(len(nets), 1, r)
        plt.grid()
        if n == nets[0]:
            plt.title(f"{what} per {against} across no. of alt. routes")
        plt.ylabel(f"{n}x{n} net\n# {what}")
        if n == nets[-1]:
            plt.xlabel(f"# {against}")
        if logx:
            plt.xscale("log")
        if logy:
            plt.yscale("log")
        for f in dirs:
            if (("altPol" in f) or ("numPol" in f)) and (f"{n}x{n}" in f and "16x16_d8" not in f and "d16_a4" not in f):
                print(f'\t{f}')
                data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
                x = data[against]
                y = data[what]
                if "numPol" in f:
                    label = 'precedence'
                else:
                    label = f"{f.replace(f'r10_{n}x{n}_d{n}_', '')}".replace('_altPol', '')
                if not limitx and not limity:
                    plt.plot(x, y, label=label, marker=markers[i], markersize=4)
                else:
                    if limitx:
                        x = data[against].where(data[against] <= limitx)
                        ticks = np.arange(0, limitx, limitx / 10)
                        if 'time' in against:
                            plt.xticks(ticks, [f'{x / 1000}' for x in ticks])  # DOC simulation time in seconds
                    if limity:
                        y = data[what].where(data[what] <= limity)
                    plt.plot(x, y, label=label, marker=markers[i], markersize=4)
                i += 1
        ax.legend()
        r += 1

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")
    return plt


def compare_2_metrics_per_breadth_scatter(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
                                  plot_target=0, output_dir="plots/prob1", limitx=None, limity=None, size=10, arrowc='red'):

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    markers = list(Line2D.markers.keys())
    #markers = ['o', '^', '8', 's', '*', '+', 'x']

    fig = plt.figure()
    plt.grid()
    plt.title(f"{what} per {against} across no. of alt. routes")
    plt.ylabel(f"# {what}")
    plt.xlabel(f"# {against}")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    dirs = os.listdir(input_dir)
    dirs.sort()
    i = 0
    texts = []
    for f in dirs:
        if "altPol" in f and "16x16_d8" not in f and "d16_a4" not in f:
            data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
            x = data.iloc[-1][against]
            y = data.iloc[-1][what]
            if not limitx and not limity:
                plt.scatter(x, y, label=f"{f.replace('r10_', '')}", marker=markers[i], s=size)
            else:
                if limitx:
                    x = data[against].where(data[against] <= limitx)
                    ticks = np.arange(0, limitx, limitx / 10)
                    if 'time' in against:
                        plt.xticks(ticks, [f'{x / 1000}' for x in ticks])  # DOC simulation time in seconds
                if limity:
                    y = data[what].where(data[what] <= limity)
                plt.scatter(x, y, label=f"{f.replace('r10_', '')}", marker=markers[i], s=size)
            texts.append(plt.text(x, y, '{:g}'.format(round(y, 2)), ha='center', va='center'))
            i += 1
    print(texts)
    adjust_text(texts, arrowprops=dict(arrowstyle='-', color=arrowc))
    plt.legend()

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")
    return plt


def compare_2_metrics_per_net(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
                                  plot_target=0, output_dir="plots/prob1", networks=[4, 8, 16], limit=None, dirs_override=None):

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    markers = list(Line2D.markers.keys())
    #markers = ['o', '^', '8', 's', '*', '+', 'x']

    fig = plt.figure()
    plt.grid()
    plt.title(f"{what} per {against} across networks")
    plt.ylabel(f"# {what}")
    plt.xlabel(f"# {against}")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    #dirs = os.listdir(input_dir)
    #dirs.sort()
    if not dirs_override:
        dirs = [f"r10_{n}x{n}_d{n}_a{int(n/2)}_altPol" for n in networks]
    else:
        dirs = dirs_override
    print(dirs)
    i = 0
    for f in dirs:
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        data.head()
        label = f'{f.split("_")[1]}_{f.split("_")[3]}'
        if not limit:
            plt.plot(data[against], data[what], label=label, marker=markers[i], markersize=4)
        else:
            plt.plot(data[against].where(data[against] <= limit), data[what], label=label, marker=markers[i], markersize=4)
            ticks = np.arange(0, limit, limit/10)
            if 'time' in against:
                plt.xticks(ticks, [f'{x/1000}' for x in ticks])  # DOC simulation time in seconds
            #plt.xticks(ticks, [f'{np.format_float_scientific(x)}' for x in ticks], fontsize=8)
        i += 1
    plt.legend()

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")


def compare_2_metrics_per_net_baseline(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
                                  plot_target=0, output_dir="plots/prob1", networks=[4, 8, 16], limitx=None, limity=None):

    # move ticks on right
    plt.rcParams['ytick.left'] = plt.rcParams['ytick.labelleft'] = False
    plt.rcParams['ytick.right'] = plt.rcParams['ytick.labelright'] = True

    # colorblind friendly
    plt.style.use('tableau-colorblind10')

    markers = list(Line2D.markers.keys())
    #markers = ['o', '^', '8', 's', '*', '+', 'x']

    fig = plt.figure()
    plt.grid()
    plt.title(f"{what} per {against} across networks")
    plt.ylabel(f"# {what}")
    plt.xlabel(f"# {against}")
    if logx:
        plt.xscale("log")
    if logy:
        plt.yscale("log")
    #dirs = os.listdir(input_dir)
    #dirs.sort()
    dirs = [f"r10_{n}x{n}_d{n}_a{int(n/2)}_altPol" for n in networks]
    dirs_ = [f"r10_{n}x{n}_d{n}_a{int(n / 2)}_numPol" for n in networks]
    dirs.extend(dirs_)
    print(dirs)
    i = 0
    for f in dirs:
        data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
        data.head()
        x = data[against]
        y = data[what]
        if not limitx and not limity:
            plt.plot(data[against], data[what], label=f"{f.replace('r10_', '')}", marker=markers[i], markersize=4)
        else:
            if limitx:
                x = data[against].where(data[against] <= limitx)
                ticks = np.arange(0, limitx, limitx / 10)
                if 'time' in against:
                    plt.xticks(ticks, [f'{x / 1000}' for x in ticks])  # DOC simulation time in seconds
            if limity:
                y = data[what].where(data[what] <= limity)
            plt.plot(x, y, label=f"{f.replace('r10_', '')}", marker=markers[i], markersize=4)
        i += 1
    plt.legend()

    fig.tight_layout()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")

