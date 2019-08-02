
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Main extends Thread   {
    public static  final int KERNEL_LENGTH = 100;

    public static void main(String[] args) throws InterruptedException {
        // Create an oscillator sample producer
        BasicOscillator osc = new BasicOscillator();
        //BasicOscillator osc2 = new BasicOscillator();
        //BasicOscillator osc3 = new BasicOscillator();

        double[] OutputKernel = new double[KERNEL_LENGTH];

        double[] state_buff_up_cutoff = new double[KERNEL_LENGTH];
        double[] state_buff_lw_cutoff = new double[KERNEL_LENGTH];

        bandpass_windowed_sinc_fltr(state_buff_lw_cutoff,
                                    state_buff_up_cutoff,
                                    OutputKernel,
                                    0.09,
                                    0.11,
                                    KERNEL_LENGTH);

        try {
            write("output_kernel.dat",OutputKernel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        

        // Set the frequency
        osc.setFrequency(262);
//        osc2.setFrequency(330);
//        osc3.setFrequency(392);
        
        // Create a sample player
        SamplePlayer player = new SamplePlayer();
        SamplePlayer player2 = new SamplePlayer();
        SamplePlayer player3 = new SamplePlayer();

        // Sets the player's sample provider
        player.setSampleProvider(osc);
//        player2.setSampleProvider(osc2);
//        player3.setSampleProvider(osc3);



        // Start the player
        player.startPlayer();
        TimeUnit.SECONDS.sleep(1);
        player.stopPlayer();
//        
//        player2.startPlayer();
//        
//        TimeUnit.SECONDS.sleep(1);
//        
//        player3.startPlayer();
        

        // Delay so oscillator can be heard
        //delay(1000 * 4);  
        

    }




    public static void bandpass_windowed_sinc_fltr( double[] lower_cutoff_state_buff,
                                                    double[] upper_cutoff_state_buff,
                                                    double[] fltr_kernel_dest_arr,
                                                    double lower_cutoff,
                                                    double upper_cutoff,
                                                    int filter_length)
    {

    //Calculate the first low-pass filter kernel
     for(int i =0;i<filter_length;i++){
         if(i-filter_length/2 ==0){
             lower_cutoff_state_buff[i] = 2 *Math.PI*lower_cutoff;
         }
         if(i - filter_length/2 !=0){
             lower_cutoff_state_buff[i] =  Math.sin(2*Math.PI*lower_cutoff*(i-filter_length/2))/(i-filter_length/2);
             lower_cutoff_state_buff[i] =  lower_cutoff_state_buff[i] *(0.42- 0.5*Math.cos(2*Math.PI*i/filter_length)+0.08*Math.cos(4*Math.PI*i/filter_length));
         }
     }

     //Calculate the second low-pass filter

        for(int i =0;i<filter_length;i++){
            if(i-filter_length/2 ==0){
                upper_cutoff_state_buff[i] =2*Math.PI*upper_cutoff;
            }
            if(i - filter_length/2 !=0){
                upper_cutoff_state_buff[i] = Math.sin(2*Math.PI*upper_cutoff*(i-filter_length/2))/(i-filter_length/2);
                upper_cutoff_state_buff[i] =  upper_cutoff_state_buff[i] *(0.42-0.5*Math.cos(2*Math.PI*i/filter_length) + 0.08*Math.cos(4*Math.PI*i/filter_length));
            }
        }
        //change lowpass filter kernel into highpass using spectral inversion

        for(int i =0;i<filter_length;i++){
            upper_cutoff_state_buff[i] = -(upper_cutoff_state_buff[i]);
        }
        upper_cutoff_state_buff[filter_length/2] =  upper_cutoff_state_buff[filter_length/2] +1;
        //Add lowpass to highpass to form band reject

        for(int i =0;i<filter_length;i++){
            fltr_kernel_dest_arr[i] =  lower_cutoff_state_buff[i] +  upper_cutoff_state_buff[i];
        }

        //change band-reject into bandpass using spectral  inversion
        for(int i =0;i<filter_length;i++){
            fltr_kernel_dest_arr[i] = -(fltr_kernel_dest_arr[i]);
        }
        fltr_kernel_dest_arr[filter_length/2] =  fltr_kernel_dest_arr[filter_length/2]+1;
    }

    public static void write(String filename, double[] x)throws IOException {

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for(int i =0;i<x.length;i++){
            outputWriter.write(Double.toString(x[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

}

