package executor.service.service;
import executor.service.model.ProxyConfigHolderDTO;
import executor.service.model.ScenarioDTO;

public interface ExecutionService {

   void execute(ScenarioDTO scenario, ProxyConfigHolderDTO proxyConfigHolderDTO) ;
}
