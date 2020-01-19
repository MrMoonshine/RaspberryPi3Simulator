use strict;
use warnings;
#use Data::Dumper qw(Dumper);
#use Cwd;
use Tie::File;
use Cwd;
use Cwd 'abs_path';
#0 = write
#1 = read
#2 = initialize
#-1 = error;

sub isVerbose{
    foreach $a (@ARGV){
        if($a eq "-v"){
            return 1;
        }
    }
    return 0;
}

sub init{
    my @params = @_;
    my $filename = "gpio/gpio".$params[0].".conf";
    my $mode = $params[1];
    my $genstr = "mode=".$mode."\nstate=low\ndutycycle=0\n";

    open(DATA,"+>".$filename) or die "FileIO error!";

    print DATA $genstr;

    close(DATA);
    if(isVerbose){print $genstr."\nfilename: ".$filename."\n";}
    
}

sub tieGpio{
    my $dir = abs_path($0);
    $dir = substr $dir, 0, -9;
    my $filename = $dir."gpio/gpio".$_[0].".conf";
    tie my @arr ,'Tie::File', $filename or die("Can't open file ".$filename);
    for my $line (@arr){
        my @fields = split /=/, $line;

        #print $fields[0]." oida ".$fields[1]."\n";
        if($fields[0] eq $_[1]){
            return $fields[1];
        }
    }
    return "";
}

sub tieWrite{
    my $dir = abs_path($0);
    $dir = substr $dir, 0, -9;
    my $filename = $dir."gpio/gpio".$_[0].".conf";
    my $payload = "";
    tie my @arr ,'Tie::File', $filename or die("Can't open file ".$filename);
    for my $line (@arr){
        my @fields = split /=/, $line;

        #print $fields[0]." oida ".$fields[1]."\n";
        if($fields[0] eq $_[1]){
            $payload .= $fields[0]."=".$_[2]."\n"
        }else{
            $payload .= $fields[0]."=".$fields[1]."\n"
        }
    }

    open(DATA,">".$filename) or die "FileIO error!";
    print DATA $payload;
    close(DATA);
}


#print Dumper \@ARGV;

#my $dir = abs_path($0);
#$dir = substr $dir, 0, -9;
#print $dir."\n";

my $length = scalar @ARGV;

my $operator = $ARGV[0];
my $id = $ARGV[1];
my $mode = $ARGV[2];

if($operator eq "-i"){
    init($id,$mode);
}elsif($operator eq "-w"){
    print tieWrite($id,$mode,$ARGV[3]);
}elsif($operator eq "-r"){
    print tieGpio($id,$mode);
}elsif($operator eq "-clean"){
    qx(rm gpio/*.conf);
}
