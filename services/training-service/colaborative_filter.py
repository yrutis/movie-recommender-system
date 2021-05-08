
from fastai.tabular.all import *
from fastai.collab import *

import torch

print(torch.__version__)

path = untar_data(URLs.ML_100k)

ratings = pd.read_csv(path / 'u.data', delimiter='\t', header=None,
                      usecols=(0, 1, 2), names=['user', 'movie', 'rating'])
ratings.head()

movies = pd.read_csv(path / 'u.item', delimiter='|', encoding='latin-1',
                     usecols=(0, 1), names=('movie', 'title'), header=None)
movies.head()

ratings.head()

# %%
movies2 = pd.read_csv(path / 'u.item', delimiter='|', encoding='latin-1',
                      names=("movie", "title", "release date", "video release date",
                             "IMDb URL", "unknown", "Action", "Adventure ", "Animation",
                             "Children's", "Comedy", "Crime ", " Documentary ", "Drama", "Fantasy",
                             "Film-Noir", "Horror ", " Musical", "Mystery ", "Romance", "Sci-Fi",
                             "Thriller", " War", " Western"))

# %%

# %% Example user ratings
action_movies = movies2[movies2['Action'] == 1][0:20]

# #%%
ratings_user_x = pd.DataFrame()
ratings_user_x["movie"] = action_movies.loc[:, 'movie']
new_user_id = ratings['user'].max() + 1
ratings_user_x["user"] = new_user_id
ratings_user_x['rating'] = 5
#
# #%% append df
ratings = ratings.append(ratings_user_x)
ratings = ratings.merge(movies)

dls = CollabDataLoaders.from_df(ratings, bs=64)

dls.show_batch()
x, y = dls.one_batch()

learn = collab_learner(dls, n_factors=50, y_range=(0, 5.5))

learn.fit_one_cycle(5, 5e-3, wd=0.1)

# for predictions
new_df = pd.DataFrame()
new_df["movie"] = movies["movie"]  # all movies
new_df["user"] = new_user_id
dl = learn.dls.test_dl(new_df)
new_df["preds"] = learn.get_preds(dl=dl)[0]
top_50_df = new_df.nlargest(50, 'preds')
top_50_df = top_50_df.merge(movies2)

print(np.sum(top_50_df['Action'] == 1) / top_50_df.shape[0])
print(np.sum(movies2['Action'] == 1) / movies2.shape[0])

# I rated action movies with 5 stars, so they should be more apparent in the df
assert (np.sum(top_50_df['Action'] == 1) / top_50_df.shape[0]) > (np.sum(movies2['Action'] == 1) / movies2.shape[0])

# TODO
# get incoming movies
# give user Id and append to df
# get predictions for all movies for this user
# return highest predictions
