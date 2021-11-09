import os
import pandas as pd

print(os.getcwd())

########## SET THESE PARAMS ##########

input_dir = "../results/prob1"
data_ext = ".csv"
aggregated_filename = "aggregate.csv"

########################################

# aggregate data creation
dirs = os.listdir(input_dir)
dirs.sort()
for d in dirs:
    dir_path = f"{input_dir}/{d}"
    if os.path.isdir(dir_path) and d not in ["1", "2", "3", "4"]:  # skip old experiments, kept for backup
        print(d)
        data_frames = []
        files = os.listdir(dir_path)
        files.sort()
        for f in files:
            file_path = f"{dir_path}/{f}"
            if os.path.isfile(file_path) and f.endswith(data_ext):
                #print(f"\t{f}")
                data_frames.append(pd.read_csv(file_path))
        h_concat_df = pd.concat(data_frames, axis=1)
        #print(h_concat_df.head())
        aggregated_df = h_concat_df.stack().groupby(level=[0, 1]).mean().unstack()
        #print(aggregated_df.head())
        aggregated_df.to_csv(f"{dir_path}/{aggregated_filename}", index=False)
#####
