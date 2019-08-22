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

> When you add a timesheet, a file named `.doing.yml`is created in your $HOME directory



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



## Commands reference

#### doing add — add a timesheet

```bash
$ doing add --help

Usage: doing add [-hvV] <name> <path>
Add timesheet
      <name>      Name of the timesheet
      <path>      Path to timesheet yaml file
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```



#### doing use — switch to a timesheet

```bash
$ doing use --help

Usage: doing use [-hvV] <name>
Select timesheet
      <name>      Name of the timesheet
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```



####doing timesheets — list timesheets

```bash
$ doing timesheets --help

Usage: doing timesheets [-hvV]
List available timesheet
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```



#### doing start — start a task

```bash
$ doing start --help

Usage: doing start [-hvV] [-s=<subproject>] [-t=<tag>] [-t another tag]... <task>
Start task
      <task>         Name of the task
  -h, --help         Show this help message and exit.
  -s, --subproject=<subproject>
                     Name of the subproject
  -t, --tag=<tags>   Add tag (can specify multiple)
  -v, --verbose      Verbose
  -V, --version      Print version information and exit.
```



#### doing stop — stop a task

```bash
$ doing stop --help

Usage: doing stop [-hvV]
Stop task
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```



#### doing continue — resume a task

```bash
$ doing continue --help

Usage: doing continue [-hvV] [<hash>]
Continue last task (duplicate & start)
      [<hash>]    Start of hash of the task you want to continue
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```



#### doing list — list tasks and calculate total time

```bash
$ doing list --help

Usage: doing list [-hvV] [-f=<filter>]
List or filter tasks
  -f, --filter=<filter>   Filter by tag or subproject
  -h, --help              Show this help message and exit.
  -v, --verbose           Verbose
  -V, --version           Print version information and exit.
```



#### doing stats — print statistics

```bash
$ doing stats --help

Usage: doing stats [-hvV] [-f=<filter>]
Show project statistics
  -f, --filter=<filter>   Filter by tag or subproject
  -h, --help              Show this help message and exit.
  -v, --verbose           Verbose
  -V, --version           Print version information and exit.
```



#### doing edit — edit the timesheet file

```bash
$ doing edit --help

Usage: doing edit [-hvV]
Edit timesheet
  -h, --help      Show this help message and exit.
  -v, --verbose   Verbose
  -V, --version   Print version information and exit.
```





## Informations

### Need help?

- Feel free to email me at <jedrzej@lewandowski.doctor>



### Would like to help?

Warmly welcomed:

- Bug reports via issues
- Enhancement requests via via issues
- Pull requests
- Security reports to jedrzej@lewandowski.doctor

---

Made with ❤️ by [Jędrzej Lewandowski](https://jedrzej.lewandowski.doctor/)
