# doing-time-tracker

Doing — java + yml + git time tracker

**Highlights**

- **YAML format**: add timesheet to the repository. Each entry is a pretty yaml object that can be easily version-checked
- **Multiple timesheets**: use one or more timesheets per each project. Switch between timesheets with `doing use [name]` command
- **Project/tag/submodule statistics** with `doing stats`

**Commands**:

```bash
$ doing --help
Usage: doing [-hV] [COMMAND]
Monitors time in yaml format
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  start, sta, r          Start task
  stop, sto, b           Stop task
  list, li, l            List or filter tasks
  add, a, ad             Add timesheet
  use, u, us             Select timesheet
  continue, c, co, cont  Continue last task (duplicate & start)
  stats, stat            Show project statistics
  edit, ed, e            Edit timesheet
  timesheets             List available timesheet
```



## Installation

```bash
# Go to directory for your custom bin installations
$ git clone http://github.com/jblew/doing-time-tracker
$ cd doing-time-tracker
$ ./gradlew clean install

$ ln -s $PWD/build/install/doing-time-tracker/bin/doing-time-tracker $YOUR_LOCAL_BIN_DIR/doing

# For example on Mac OS X the last cmd would be:
$ ln -s $PWD/build/install/doing-time-tracker/bin/doing-time-tracker /Users/$USER/.local/bin/doing
```



## Usage example

**Step 1** Add timesheet

```bash
# Go to your project dir
$ doing add [PROJECT_NAME] ./_dev/timesheet.yml

# List available timesheets
$ doing list

# Use your timesheet
$ doing use [PROJECT_NAME]
```

**Step 2** Manipulate tasks

```bash
# You can specify a subproject and multiple tags. All parameters are optional.
$ doing start --tag refactoring --tag v3 --subproject cli-tool "Huge refactoring"

# Stop task
$ doing stop

# Resume last task
$ doing continue

# List tasks
$ doing list

# Filter tasks
$ doing list -f refactoring # Will show tasks with refactoring tag

# Resume spefic task by hash
$ doing continue tu98 # this four letter hashes are printed by the `doing list` command
```

**Step 3**: Make corrections in the timesheet

```bash
# Will open vim on the last line
$ doing edit
```

**Step 4**: Summarize your work

```bash
$ doing stats

# Summarize work on specific tag or subproject:
$ doing stats -f cli-tool # Will show only stats for `cli-tool` subproject
```



