Note: Once cloning this repo, do the following steps:
1. Run the following command to prevent Git from tracking any changes in ".env" file:  
git update-index --skip-worktree ".env"  
2. Change the value in file ".env" as appropriate.
* To change branch, change the content of ".env" file in its origin content and run this command:  
git update-index --no-skip-worktree ".env"  