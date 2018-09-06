package services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.HashMap;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.PythonProgram;
import entities.StreamListner;

@RestController
public class NoteBookController {

	static java.util.Map<String, Process> allProcess = new HashMap<>();

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public java.util.Map<String, String> getPythonCode(HttpServletRequest request,
			@RequestBody PythonProgram pythonProgram) {

		String pythonFinalCode = null;
		String pythonFinalResult = null;
		StringBuilder inputString = new StringBuilder();
		Process p = null;
		ProcessBuilder pb = null;
		if (pythonProgram.getCode().startsWith("%python ")) {
			pythonFinalCode = pythonProgram.getCode().substring(8);
			try {
				String session_id = request.getSession().getId();
				if (allProcess.get(session_id) != null) {

					p = allProcess.get(session_id);


				} else {

					pb = new ProcessBuilder("python", "-i");
					p = pb.start();
					allProcess.put(session_id, p);

				}

				StreamListner streamListner = new StreamListner(p.getInputStream(), inputString);
				StreamListner streamListner_error = new StreamListner(p.getErrorStream(), inputString);

				BufferedWriter stdOutput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

				streamListner_error.start();
				streamListner.start();
				p.waitFor(2, TimeUnit.SECONDS);

				stdOutput.write(pythonFinalCode);
				stdOutput.newLine();
				stdOutput.flush();
				
				p.waitFor(1, TimeUnit.SECONDS);
				streamListner_error.setStop(true);
				streamListner.setStop(true);

				pythonFinalResult = streamListner.getStrBuilder().toString();

			} catch (IOException e) {
				// TODO: handle exception
				pythonFinalResult = "exception happened " + e.getMessage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			pythonFinalResult = "error";
		}

		java.util.Map<String, String> pythonResult = new HashMap<>();
		pythonResult.put("result", pythonFinalResult);
		return pythonResult;
	}

}
