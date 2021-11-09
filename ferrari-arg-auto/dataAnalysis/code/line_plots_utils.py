import os
import pandas as pd
import matplotlib.pyplot as plt


def plot(what, against, comp_filename, input_dir="results/prob1", data_filename="aggregate.csv", logx=False, logy=False,
         plot_target=0, output_dir="plots/prob1"):

    plt.figure()
    plt.grid()
    plt.title(f"{what} per {against} across policies")
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
        if "_d16_" not in f and "altPol" in f:  # filter out work in progress experiments
            data = pd.read_csv(f'{input_dir}/{f}/{data_filename}')
            plt.plot(data[against], data[what], label=f"{f.replace('r10_', '')}", markersize=4)
            i += 1
    plt.legend()

    if plot_target == 0:
        plt.show()
    else:
        if not os.path.exists(f"{output_dir}"):
            os.mkdir(f"{output_dir}")
        plt.savefig(f"{output_dir}/{comp_filename}")


plot("crossings", "alternative_routes_used", "comparison-servedXaltRoutes-policy.pdf")
plot("crossings", "alternative_routes_used", "comparison-servedXaltRoutes-policy.pdf", logx=True, logy=True)
