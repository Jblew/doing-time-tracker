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

