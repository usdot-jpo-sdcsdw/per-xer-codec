# Define a variable for classpath
CLASS_PATH = ../target/classes

# Debug flags:
# -g3=compile with extra debugg infos. 
# -ggdbg3=include things like macro defenitions. 
# -O0=turn off optimizations.
# -Wall = With all warnings.
DEBUGFLAGS = -g3 -ggdb3 -O0 -Wall

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)

# $* matches the target filename without the extension
# manually this would be: javah -classpath ../bin HelloJNI
gov_dot_its_jpo_sdcsdw_PerXerCodec.h : gov/dot/its/jpo/sdcsdw/PerXerCodec.class
	javah -classpath $(CLASS_PATH) $*