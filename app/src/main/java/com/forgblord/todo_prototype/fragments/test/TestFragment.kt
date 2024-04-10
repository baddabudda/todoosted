package com.forgblord.todo_prototype.fragments.test

//class TestFragment: Fragment(), TaskInterface {
//    private var _binding: FragmentTestBinding? = null
//    private val binding
//        get() = checkNotNull(_binding) {
//            "Cannot access binding because it is null. Is the view visible?"
//        }
//
//    private val taskListViewModel: TaskListViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentTestBinding.inflate(inflater, container, false)
//        binding.rvTestInbox.layoutManager = LinearLayoutManager(context)
//        binding.rvTestToday.layoutManager = LinearLayoutManager(context)
//
//        val inbox = taskListViewModel.getAllTasks()
//        val today = taskListViewModel.getAllDueToday()
//
//        binding.rvTestInbox.adapter = TaskListAdapter(inbox, this)
//        binding.rvTestToday.adapter = TaskListAdapter(today, this)
//
//        return binding.root
//    }
//
//    override fun removeById(id: UUID) {
//        taskListViewModel.removeTaskById(id)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}